package com.example.chatapp.data

import com.example.chatapp.data.dto.MessageModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    companion object {
        private const val baseUrl: String = "https://supportbot.hakta.pro/api/"


        private val gson: Gson = GsonBuilder().run {
            setLenient()

            create()
        }

        private val client: OkHttpClient = OkHttpClient.Builder().run {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            )

            build()
        }

        private val retrofit: Retrofit = Retrofit.Builder().run {
            baseUrl(baseUrl)

            client(client)

            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create(gson))

            build()
        }

        val api: Api = retrofit.create(Api::class.java)
    }

    @POST("message")
    fun sendMessage(@Body message: MessageModel): Observable<MessageModel>
}
