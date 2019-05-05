package me.mqueiroz.github.model

import io.reactivex.Single
import io.reactivex.observers.TestObserver
import me.mqueiroz.github.model.GithubRepository
import me.mqueiroz.github.model.GithubRepositoryImpl
import me.mqueiroz.github.model.data.Repo
import me.mqueiroz.github.model.network.GetReposResponse
import me.mqueiroz.github.model.network.GithubService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.MockitoAnnotations

@RunWith(MockitoJUnitRunner::class)
class GithubRepositoryImplTest {

    @Mock
    lateinit var githubService: GithubService

    private lateinit var githubRepository: GithubRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        githubRepository = GithubRepositoryImpl(githubService)
    }

    @Test
    fun getRepos_ShouldStreamAPIResponseItems() {
        // given
        val items: List<Repo> = emptyList()
        val size = items.size
        val incompleteResults = false
        val response = GetReposResponse(size, incompleteResults, items)

        `when`(githubService.getRepos()).thenReturn(Single.just(response))

        // when
        val subscriber = TestObserver<List<Repo>>()
        githubRepository.getRepos().subscribe(subscriber)

        // then
        subscriber.assertValue(items)
    }
}