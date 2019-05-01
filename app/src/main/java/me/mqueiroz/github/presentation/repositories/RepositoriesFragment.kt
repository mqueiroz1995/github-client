package me.mqueiroz.github.presentation.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.mqueiroz.github.R

class RepositoriesFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(): RepositoriesFragment {
            return RepositoriesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repositories, container, false)
    }

}