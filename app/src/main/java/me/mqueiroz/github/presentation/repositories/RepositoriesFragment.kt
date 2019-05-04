package me.mqueiroz.github.presentation.repositories

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

        val githubRepository = GithubRepositoryImpl()
        val factory = ReposViewModel.Factory(githubRepository)
        viewModel = ViewModelProviders.of(this, factory).get(ReposViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repositories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ReposAdapter(context!!)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter

        viewModel.repos.observe(this, Observer { repos ->
            adapter.setRepos(repos)
        })
    }
}