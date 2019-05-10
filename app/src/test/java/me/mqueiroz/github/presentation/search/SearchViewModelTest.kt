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
class SearchViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var githubRepository: GithubRepository

    @Mock
    lateinit var stateObserver: Observer<SearchFragmentState>

    private val schedulerProvider = TrampolineSchedulerProvider()

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = SearchViewModel(schedulerProvider, githubRepository)
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
            verify(stateObserver).onChanged(SearchFragmentState.Loading)
            verify(stateObserver).onChanged(SearchFragmentState.Loaded(items))
            verifyNoMoreInteractions(stateObserver)
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
            verify(stateObserver).onChanged(SearchFragmentState.Loading)
            verify(stateObserver).onChanged(SearchFragmentState.Error(exception.message))
            verifyNoMoreInteractions(stateObserver)
        }
    }


    @After
    fun tearDown() {
        viewModel.state.removeObserver(stateObserver)
    }
}