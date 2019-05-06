package me.mqueiroz.github.presentation.repos

import android.view.View
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_repo.view.*
import me.mqueiroz.github.R
import me.mqueiroz.github.model.data.Repo
import me.mqueiroz.github.presentation.base.BaseViewHolder
import java.lang.Exception

class RepoViewHolder(
        itemView: View
) : BaseViewHolder<Repo>(itemView) {

    private val name = itemView.repo_name
    private val description = itemView.repo_description
    private val forks = itemView.repo_forks
    private val stars = itemView.repo_stars
    private val image = itemView.repo_image
    private val ownerUsername = itemView.repo_owner_username
    private val progressBar = itemView.progressBar

    override fun bind(obj: Repo) {
        name.text = obj.name
        description.text = obj.description
        forks.text = obj.forksCount.toString()
        stars.text = obj.stargazersCount.toString()
        ownerUsername.text = obj.owner.login

        progressBar.visibility = View.VISIBLE
        Picasso.get()
                .load(obj.owner.avatarUrl)
                .error(R.drawable.ic_error_outline)
                .into(image, object : Callback {
                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        progressBar.visibility = View.GONE
                    }
                })
    }
}