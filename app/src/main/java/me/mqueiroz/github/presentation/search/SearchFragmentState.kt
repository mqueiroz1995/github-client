package me.mqueiroz.github.presentation.search

import me.mqueiroz.github.model.data.Repo

sealed class SearchFragmentState {
    object Loading : SearchFragmentState()
    data class Error(val message: String?) : SearchFragmentState()
    data class Loaded(val repos: List<Repo>) : SearchFragmentState()
}