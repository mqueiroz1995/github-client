package me.mqueiroz.github.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.mqueiroz.github.R
import me.mqueiroz.github.presentation.repos.ReposFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content, ReposFragment.newInstance())
                    .commit()
        }
    }
}
