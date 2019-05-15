package com.example.viewmodel

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.viewmodel.databinding.ItemRepoBinding
import com.example.viewmodel.model.Repository
import com.example.viewmodel.view_model.ItemRepoViewModel

class RepositoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var repositories: List<Repository> = listOf()

    fun setRepositories(repositories: List<Repository>) {
        this.repositories = repositories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemRepoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_repo,
            parent,
            false
        )
        return RepositoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (repositories.isEmpty()) 0 else repositories.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, positiion: Int) {
        holder as RepositoryViewHolder
        holder.bindRepository(repositories[positiion])
    }

    class RepositoryViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.cardView) {
        fun bindRepository(repository: Repository) {
            if (binding.viewModel == null) {
                binding.viewModel = ItemRepoViewModel(itemView.context, repository)
            } else {
                binding.viewModel!!.setRepository(repository)
            }
        }
    }
}