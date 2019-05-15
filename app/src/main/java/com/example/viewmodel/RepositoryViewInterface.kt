package com.example.viewmodel

import com.example.viewmodel.base.BaseViewInterface
import com.example.viewmodel.model.Repository

interface RepositoryViewInterface : BaseViewInterface {
    fun loadSuccess(data: List<Repository>?)
}