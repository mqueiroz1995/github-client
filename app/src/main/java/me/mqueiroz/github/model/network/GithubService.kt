package me.mqueiroz.github.model.network

import io.reactivex.Single
import retrofit2.http.GET

interface GithubService {

    @GET("search/repositories?q=language:Java&sort=stars&page=1")
    fun getRepos(): Single<GetReposResponse>
}