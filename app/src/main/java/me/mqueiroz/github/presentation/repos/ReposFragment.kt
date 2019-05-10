package me.mqueiroz.github.presentation.repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_repositories.*
import me.mqueiroz.github.R
import me.mqueiroz.github.model.GithubRepositoryImpl
import me.mqueiroz.github.model.data.Repo
import me.mqueiroz.github.model.network.GithubService
import me.mqueiroz.github.utils.schedulers.RuntimeSchedulerProvider
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class ReposFragment : Fragment() {

    private lateinit var adapter: ReposAdapter
    private lateinit var viewModel: ReposViewModel

    companion object {
        @JvmStatic
        fun newInstance(): ReposFragment {
            return ReposFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val api = retrofit.create(GithubService::class.java)

        val schedulerProvider = RuntimeSchedulerProvider()
        val githubRepository = GithubRepositoryImpl(api)
        val factory = ReposViewModel.Factory(schedulerProvider, githubRepository)
        viewModel = ViewModelProviders.of(this, factory).get(ReposViewModel::class.java)
        viewModel.getRepos()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repositories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ReposAdapter()
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE

        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is ReposViewState.Loading -> onLoading()
                is ReposViewState.Error -> onError(state.message)
                is ReposViewState.Loaded -> onLoaded(state.repos)
            }
        })
    }

    private fun onLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun onError(message: String?) {
        progressBar.visibility = View.GONE
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun onLoaded(repos: List<Repo>) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        adapter.setRepos(repos)
    }
}