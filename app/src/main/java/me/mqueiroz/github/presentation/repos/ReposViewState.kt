package me.mqueiroz.github.presentation.repos

import me.mqueiroz.github.model.data.Repo

sealed class ReposViewState {
    object Loading : ReposViewState()
    data class Error(val message: String?) : ReposViewState()
    data class Loaded(val repos: List<Repo>) : ReposViewState()
}