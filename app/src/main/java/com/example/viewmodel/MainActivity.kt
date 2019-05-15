package com.example.viewmodel

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.viewmodel.databinding.ActivityMainBinding
import com.example.viewmodel.model.Repository
import com.example.viewmodel.view_model.MainViewModel
import kotlin.math.log

class MainActivity : AppCompatActivity(), MainViewModel.DataListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: RepositoryAdapter

    private var dataListener: MainViewModel.DataListener = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = MainViewModel(this@MainActivity, dataListener)
        binding.viewModel = mainViewModel
        setSupportActionBar(binding.toolbar)
        setupRecyclerView(binding.reposRecyclerView)
    }

    override fun onRepositoriesChanged(repositories: List<Repository>) {
        adapter.setRepositories(repositories)
        hideSoftKeyboard()
    }

    private fun setupRecyclerView(reposRecyclerView: RecyclerView) {
        adapter = RepositoryAdapter()
        reposRecyclerView.setHasFixedSize(true)
        reposRecyclerView.layoutManager = LinearLayoutManager(this)
        reposRecyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.destroy()
    }

    private fun hideSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextUsername.windowToken, 0)
    }

    private fun log(s: String) {
        Log.d("MainActivityA", s)
    }
}
