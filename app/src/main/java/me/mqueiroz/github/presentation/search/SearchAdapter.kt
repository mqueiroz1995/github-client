package me.mqueiroz.github.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.mqueiroz.github.R
import me.mqueiroz.github.model.data.Repo
import me.mqueiroz.github.utils.SingleLiveEvent

class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {

    private var repos = emptyList<Repo>()

    val onItemClickListener = SingleLiveEvent<Repo>()

    fun setRepos(repos: List<Repo>) {
        this.repos = repos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_repo, parent, false)

        return SearchViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(repos[position])
    }

    override fun getItemCount(): Int {
        return repos.size
    }
}