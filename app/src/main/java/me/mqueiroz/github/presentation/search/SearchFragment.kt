package me.mqueiroz.github.presentation.search

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
import kotlinx.android.synthetic.main.fragment_search.*
import me.mqueiroz.github.R
import me.mqueiroz.github.model.GithubRepositoryImpl
import me.mqueiroz.github.model.data.Repo
import me.mqueiroz.github.model.network.GithubService
import me.mqueiroz.github.utils.schedulers.RuntimeSchedulerProvider
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class SearchFragment : Fragment() {

    private lateinit var adapter: SearchAdapter
    private lateinit var viewModel: SearchViewModel

    companion object {
        @JvmStatic
        fun newInstance(): SearchFragment {
            return SearchFragment()
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
        val factory = SearchViewModel.Factory(schedulerProvider, githubRepository)
        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)
        viewModel.getRepos()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initProgressBar()

        bindViewModel()
        bindRecyclerViewAdapter()
    }

    private fun initRecyclerView() {
        adapter = SearchAdapter()
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        recyclerView.visibility = View.GONE
    }

    private fun initProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun bindViewModel() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is SearchFragmentState.Loading -> onLoading()
                is SearchFragmentState.Error -> onError(state.message)
                is SearchFragmentState.Loaded -> onLoaded(state.repos)
            }
        })
    }

    private fun bindRecyclerViewAdapter() {
        adapter.onItemClickListener.observe(this, Observer {
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