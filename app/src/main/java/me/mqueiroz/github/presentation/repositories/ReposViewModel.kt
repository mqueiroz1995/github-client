package me.mqueiroz.github.presentation.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.mqueiroz.github.model.GithubRepository
import me.mqueiroz.github.model.data.Repo

class ReposViewModel(
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val mRepos = MutableLiveData<List<Repo>>()

    val repos: LiveData<List<Repo>> = mRepos

    init {
        mRepos.value = githubRepository.getRepos()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val githubRepository: GithubRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ReposViewModel(githubRepository) as T
        }
    }
}