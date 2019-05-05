package me.mqueiroz.github.presentation.repos

import android.view.View
import kotlinx.android.synthetic.main.list_item_repo.view.*
import me.mqueiroz.github.model.data.Repo
import me.mqueiroz.github.presentation.base.BaseViewHolder

class RepoViewHolder(
        itemView: View
) : BaseViewHolder<Repo>(itemView) {

    private val title = itemView.textView

    override fun bind(obj: Repo) {
        title.text = obj.name
    }
}