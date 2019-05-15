package com.example.viewmodel.view_model

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.viewmodel.R
import com.example.viewmodel.RepositoryViewInterface
import com.example.viewmodel.base.ViewModel
import com.example.viewmodel.manager.RetrofitManager
import com.example.viewmodel.model.Repository
import rx.Subscription


const val TAG = "MainViewModelA"

class MainViewModel(private var context: Context, private var dataListener: DataListener) : ViewModel, RepositoryViewInterface {

    private var repositoryViewInterface: RepositoryViewInterface = this
    private lateinit var retrofitManager: RetrofitManager

    private var subscription: Subscription? = null
    private var editTextUsernameValue = ""

    var infoMessageVisibility: ObservableInt = ObservableInt(View.VISIBLE)
    var progressVisibility: ObservableInt = ObservableInt(View.INVISIBLE)
    var recyclerViewVisibility: ObservableInt = ObservableInt(View.INVISIBLE)
    var infoMessage: ObservableField<String> = ObservableField(context.getString(R.string.default_info_message))

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////// LISTENER //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    fun onClickSearch(v: View) {
        if (editTextUsernameValue.isEmpty()) {
            return
        }
        loadGithubRepos(editTextUsernameValue)
    }

    val usernameEditTextWatcher: TextWatcher
        get() = object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editTextUsernameValue = s.toString()
            }
        }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////// PROCESS //////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private fun loadGithubRepos(username: String) {
        retrofitManager = RetrofitManager(context, repositoryViewInterface)
        subscription = retrofitManager.publicRepositories(username)
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////// OVERRIDE INTERFACE ////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun destroy() {
        if (subscription != null && !subscription!!.isUnsubscribed) {
            subscription!!.unsubscribe()
        }
        subscription = null
    }

    override fun showLoading() {
        progressVisibility.set(View.VISIBLE)
        recyclerViewVisibility.set(View.INVISIBLE)
        infoMessageVisibility.set(View.INVISIBLE)
    }

    override fun hideLoading() {
        progressVisibility.set(View.INVISIBLE)
    }

    override fun noInterface(message: String) {
        hideLoading()
    }

    override fun loadFailed(message: String) {
        hideLoading()
        infoMessageVisibility.set(View.VISIBLE)
        infoMessage.set(message)
    }

    override fun loadSuccess(data: List<Repository>?) {
        hideLoading()
        infoMessageVisibility.set(View.INVISIBLE)
        if (data != null) {
            dataListener.onRepositoriesChanged(data)
            recyclerViewVisibility.set(View.VISIBLE)
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////// TOOLS ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    interface DataListener {
        fun onRepositoriesChanged(repositories: List<Repository>)
    }

    private fun log(s: String) {
        Log.d(TAG, s)
    }

    private fun toast(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }
}