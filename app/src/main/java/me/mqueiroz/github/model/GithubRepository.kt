package me.mqueiroz.github.model

import me.mqueiroz.github.model.data.Repo

interface GithubRepository {

    fun getRepos(): List<Repo>
}