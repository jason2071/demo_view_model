package com.example.viewmodel.view_model

import android.content.Context
import android.databinding.BaseObservable
import android.view.View
import android.widget.Toast
import com.example.viewmodel.R
import com.example.viewmodel.base.ViewModel
import com.example.viewmodel.model.Repository

class ItemRepoViewModel(
    private var context: Context,
    private var repository: Repository
) : BaseObservable(), ViewModel {

    val name: String
        get() = repository.name!!

    fun getDescription(): String? {
        return repository.description
    }

    fun getStars(): String? {
        return context.getString(R.string.text_stars, repository.stargazers_count)
    }

    fun getWatchers(): String? {
        return context.getString(R.string.text_watchers, repository.watchers)
    }

    fun getForks(): String? {
        return context.getString(R.string.text_forks, repository.forks)
    }

    fun onItemClick(view: View) {
        Toast.makeText(context, repository.name, Toast.LENGTH_SHORT).show()
    }

    fun setRepository(repository: Repository) {
        this.repository = repository
        notifyChange()
    }

    override fun destroy() {
    }
}