package me.mqueiroz.github.presentation.repos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Observable
import me.mqueiroz.github.model.GithubRepository
import me.mqueiroz.github.model.data.Repo
import me.mqueiroz.github.model.network.GetReposResponse
import me.mqueiroz.github.presentation.repos.ReposViewModel
import me.mqueiroz.github.utils.schedulers.TrampolineSchedulerProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ReposViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var githubRepository: GithubRepository

    private val schedulerProvider = TrampolineSchedulerProvider()

    private lateinit var viewModel: ReposViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun init_ShouldSubscribeToRepository() {
        // given
        val items: List<Repo> = emptyList()
        `when`(githubRepository.getRepos()).thenReturn(Observable.just(items))

        // when
        val viewModel = ReposViewModel(schedulerProvider, githubRepository)

        // then
        assertEquals(viewModel.repos.value, items)
    }
}