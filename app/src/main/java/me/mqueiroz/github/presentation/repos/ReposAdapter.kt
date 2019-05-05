package me.mqueiroz.github.presentation.repos

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.mqueiroz.github.R
import me.mqueiroz.github.model.data.Repo

class ReposAdapter : RecyclerView.Adapter<RepoViewHolder>() {

    private var repos = emptyList<Repo>()

    fun setRepos(repos: List<Repo>) {
        this.repos = repos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_repo, parent, false)

        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(repos[position])
    }

    override fun getItemCount(): Int {
        return repos.size
    }
}