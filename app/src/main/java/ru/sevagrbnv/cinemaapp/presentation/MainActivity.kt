package ru.sevagrbnv.cinemaapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.sevagrbnv.cinemaapp.R
import ru.sevagrbnv.cinemaapp.presentation.cinemaDetail.DetailFragment
import ru.sevagrbnv.cinemaapp.presentation.cinemaList.CinemaListFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DetailFragment.OpenPrevFragment {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, CinemaListFragment())
                .commit()
    }

    override fun back() {
        supportFragmentManager.popBackStack()
    }

}