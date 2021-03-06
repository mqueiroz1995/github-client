package me.mqueiroz.github.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable
import me.mqueiroz.github.model.GithubRepository
import me.mqueiroz.github.utils.schedulers.SchedulerProvider

class SearchViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val mState = MutableLiveData<SearchFragmentState>()

    val state: LiveData<SearchFragmentState> = mState

    private val disposable = CompositeDisposable()

    fun getRepos() {
        disposable.add(githubRepository.getRepos()
            .subscribeOn(schedulerProvider.computation())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { mState.value = SearchFragmentState.Loading }
            .subscribe(
                { repos -> mState.value = SearchFragmentState.Loaded(repos) },
                { mState.value = SearchFragmentState.Error(it.message) })
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
            return SearchViewModel(schedulerProvider, githubRepository) as T
        }
    }
}