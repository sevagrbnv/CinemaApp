package ru.sevagrbnv.cinemaapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.sevagrbnv.cinemaapp.R
import ru.sevagrbnv.cinemaapp.di.App
import ru.sevagrbnv.cinemaapp.presentation.cinemaDetail.DetailFragment
import ru.sevagrbnv.cinemaapp.presentation.cinemaList.CinemaListFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DetailFragment.OpenPrevFragment {

//    private val component by lazy {
//        (application as App).component
//    }

    override fun onCreate(savedInstanceState: Bundle?) {

//        component.inject(this)

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