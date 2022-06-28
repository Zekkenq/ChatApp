package com.example.chatapp.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.adapter.ChatAdapter
import com.example.chatapp.data.Api
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.data.dto.MessageModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id: String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val listMessages = mutableListOf<String>()

        binding.rv.adapter = ChatAdapter(this, listMessages)
        binding.rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)

        binding.backArrow.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnSend.setOnClickListener {
            val text: String = binding.edtChat.text
                .toString()
                .trimStart()
                .trimEnd()

            if (text.isNotEmpty()) {
                binding.btnSend.isEnabled = false

                val disp = Api.api
                    .sendMessage(MessageModel(id, text))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        binding.btnSend.isEnabled = true
                        listMessages.add(0, text)
                        binding.rv.adapter!!.notifyItemInserted(0)
                        binding.rv.scrollToPosition(0)

                        binding.edtChat.text.clear()
                    }, { e ->
                        Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                    })
            }
        }
    }
}