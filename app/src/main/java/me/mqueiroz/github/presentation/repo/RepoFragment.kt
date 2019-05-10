package me.mqueiroz.github.presentation.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_repo.*
import me.mqueiroz.github.R

class RepoFragment : Fragment() {

    private val args: RepoFragmentArgs by navArgs()

    private lateinit var viewModel: RepoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = RepoViewModel.Factory(args.repo)
        viewModel = ViewModelProviders.of(this, factory).get(RepoViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text.text = viewModel.test
    }
}