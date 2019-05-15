package com.example.viewmodel.util

import retrofit2.adapter.rxjava.HttpException

object NetworkUtil {

    fun isHttp404(error: Throwable): Boolean {
        return error is HttpException && error.code() == 404
    }
}