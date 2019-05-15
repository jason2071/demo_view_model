package com.example.viewmodel.manager

import android.content.Context
import com.example.viewmodel.R
import com.example.viewmodel.RepositoryViewInterface
import com.example.viewmodel.model.Repository
import com.example.viewmodel.util.NetworkUtil
import retrofit2.adapter.rxjava.HttpException
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RetrofitManager(private val context: Context, private val repositoryViewInterface: RepositoryViewInterface) {

    private val apiService: ApiService = RetrofitClient.apiService
    private var repositories = listOf<Repository>()

    fun publicRepositories(username: String): Subscription {

        // check network

        // show loading
        repositoryViewInterface.showLoading()

        // call api
        return apiService.publicRepositories(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<List<Repository>>() {
                override fun onNext(t: List<Repository>?) {
                    if (t != null) repositories = t
                }

                override fun onCompleted() {
                    if (repositories.isNotEmpty()) {
                        repositoryViewInterface.loadSuccess(repositories)
                    } else {
                        repositoryViewInterface.loadFailed(context.getString(R.string.text_empty_repos))
                    }
                    repositoryViewInterface.hideLoading()
                }

                override fun onError(e: Throwable?) {
                    if (NetworkUtil.isHttp404(e!!)) {
                        repositoryViewInterface.loadFailed(context.getString(R.string.error_username_not_found))
                    } else {
                        repositoryViewInterface.loadFailed(context.getString(R.string.error_loading_repos))
                    }
                    repositoryViewInterface.hideLoading()
                }
            })
    }
}