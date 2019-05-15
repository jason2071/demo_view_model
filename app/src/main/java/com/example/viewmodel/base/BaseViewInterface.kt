package com.example.viewmodel.base

interface BaseViewInterface {
    fun showLoading()
    fun hideLoading()
    fun noInterface(message: String)
    fun loadFailed(message: String)
}