package com.example.viewmodel.manager

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var retrofit: Retrofit? = null
    private var baseUrl = "https://api.github.com/"

    val apiService: ApiService
        get() = getClient(baseUrl)

    private fun getClient (baseUrl: String): ApiService {
        if(retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        }
        return retrofit!!.create(ApiService::class.java)
    }
}