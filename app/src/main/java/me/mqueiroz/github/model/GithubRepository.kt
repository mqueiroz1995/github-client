package me.mqueiroz.github.model

import io.reactivex.Observable
import me.mqueiroz.github.model.data.Repo

interface GithubRepository {

    fun getRepos(): Observable<List<Repo>>
}