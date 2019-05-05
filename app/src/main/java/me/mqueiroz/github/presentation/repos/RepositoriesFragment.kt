package me.mqueiroz.github.presentation.repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_repositories.*
import me.mqueiroz.github.R
import me.mqueiroz.github.model.GithubRepositoryImpl
import me.mqueiroz.github.model.network.GithubService
import me.mqueiroz.github.utils.schedulers.RuntimeSchedulerProvider
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class RepositoriesFragment : Fragment() {

    private lateinit var adapter: ReposAdapter
    private lateinit var viewModel: ReposViewModel

    companion object {
        @JvmStatic
        fun newInstance(): RepositoriesFragment {
            return RepositoriesFragment()
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
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repositories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.layoutManager = LinearLayoutManager(context)
        adapter = ReposAdapter()
        recycler_view.adapter = adapter

        viewModel.repos.observe(this, Observer { repos ->
            adapter.setRepos(repos)
        })
    }
}