package me.mqueiroz.github.presentation.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.mqueiroz.github.model.data.Repo

class RepoViewModel(
    val repo: Repo
) : ViewModel() {

    val test = repo.name

    class Factory(
        val repo: Repo
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RepoViewModel(repo) as T
        }
    }
}