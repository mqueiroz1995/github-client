package me.mqueiroz.github.model

import me.mqueiroz.github.model.data.Repo
import java.util.*

class GithubRepositoryImpl : GithubRepository {
    override fun getRepos(): List<Repo> {
        val repos = LinkedList<Repo>()
        repos.add(Repo("test_1"))
        repos.add(Repo("test_2"))
        repos.add(Repo("test_3"))
        repos.add(Repo("test_4"))
        repos.add(Repo("test_5"))

        return repos
    }
}