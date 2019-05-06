package me.mqueiroz.github.presentation.repos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import me.mqueiroz.github.model.GithubRepository
import me.mqueiroz.github.model.data.Repo
import me.mqueiroz.github.utils.schedulers.TrampolineSchedulerProvider
import org.junit.*
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

    @Mock
    lateinit var stateObserver: Observer<ReposViewState>

    private val schedulerProvider = TrampolineSchedulerProvider()

    private lateinit var viewModel: ReposViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = ReposViewModel(schedulerProvider, githubRepository)
        viewModel.state.observeForever(stateObserver)
    }

    @Test
    fun getRepos_ShouldSetLoadedState_OnNext() {
        // given
        val items: List<Repo> = emptyList()
        `when`(githubRepository.getRepos()).thenReturn(Observable.just(items))

        // when
        viewModel.getRepos()

        // then
        inOrder(stateObserver) {
            verify(stateObserver).onChanged(ReposViewState.Loading)
            verify(stateObserver).onChanged(ReposViewState.Loaded(items))
        }
    }

    @Test
    fun getRepos_ShouldSetErrorState_OnError() {
        // given
        val exception = RuntimeException("TestError")
        `when`(githubRepository.getRepos()).thenReturn(Observable.error(exception))

        // when
        viewModel.getRepos()

        // then
        inOrder(stateObserver) {
            verify(stateObserver).onChanged(ReposViewState.Loading)
            verify(stateObserver).onChanged(ReposViewState.Error(exception.message))
        }
    }


    @After
    fun tearDown() {
        viewModel.state.removeObserver(stateObserver)
    }
}