package com.example.viewmodel.manager

import com.example.viewmodel.model.Repository
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface ApiService {

    @GET("users/{username}/repos")
    fun publicRepositories(@Path("username") username: String): Observable<List<Repository>>
}