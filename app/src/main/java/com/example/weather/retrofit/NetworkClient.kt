package com.example.weather.retrofit

import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkClient {
    var retrofit: Retrofit? = null
    var appCompatActivity : AppCompatActivity? =null
    fun  get(baseUrl1: String): Retrofit?{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(100000, TimeUnit.MILLISECONDS)
            .readTimeout(100000, TimeUnit.MILLISECONDS)
            .writeTimeout(100000, TimeUnit.MILLISECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl1)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) // Convertor library used to convert response into POJO
            .build()
        return retrofit
    }
}