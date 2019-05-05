package me.mqueiroz.github.presentation.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable
import me.mqueiroz.github.model.GithubRepository
import me.mqueiroz.github.model.data.Repo
import me.mqueiroz.github.utils.schedulers.SchedulerProvider

class ReposViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val mRepos = MutableLiveData<List<Repo>>()

    val repos: LiveData<List<Repo>> = mRepos

    private val disposable = CompositeDisposable()

    init {
        getRepos()
    }

    private fun getRepos() {
        disposable.add(
            githubRepository.getRepos()
                .subscribeOn(schedulerProvider.computation())
                .observeOn(schedulerProvider.ui())
                .doOnNext { repos ->
                    mRepos.value = repos
                }.subscribe()
        )
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val schedulerProvider: SchedulerProvider,
        private val githubRepository: GithubRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ReposViewModel(schedulerProvider, githubRepository) as T
        }
    }
}