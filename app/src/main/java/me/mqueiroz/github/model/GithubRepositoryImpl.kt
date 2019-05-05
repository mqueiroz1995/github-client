package me.mqueiroz.github.model

import io.reactivex.Observable
import me.mqueiroz.github.model.data.Repo
import me.mqueiroz.github.model.network.GithubService

class GithubRepositoryImpl(
    private val githubService: GithubService
) : GithubRepository {

    override fun getRepos(): Observable<List<Repo>> {
        return githubService.getRepos()
            .map { response -> response.items }
            .toObservable()
    }
}