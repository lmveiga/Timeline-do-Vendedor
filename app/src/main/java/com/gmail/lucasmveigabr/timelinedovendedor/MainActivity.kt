package com.gmail.lucasmveigabr.timelinedovendedor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.gmail.lucasmveigabr.timelinedovendedor.core.NavigationEvent.AddTaskNavigation
import com.gmail.lucasmveigabr.timelinedovendedor.core.NavigationEvent.TimelineNavigation
import com.gmail.lucasmveigabr.timelinedovendedor.core.NavigationViewModel
import com.gmail.lucasmveigabr.timelinedovendedor.feature.addtask.AddTaskFragment
import com.gmail.lucasmveigabr.timelinedovendedor.feature.timeline.TimelineFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val navigationViewModel: NavigationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TimelineFragment.newInstance())
                    .commitNow()
        }
        navigationViewModel.navigation.observe(this, Observer {
            when (it) {
                TimelineNavigation -> supportFragmentManager.beginTransaction()
                    .replace(R.id.container, TimelineFragment.newInstance()).commit()
                AddTaskNavigation -> supportFragmentManager.beginTransaction()
                    .replace(R.id.container, AddTaskFragment.newInstance())
                    .commit()
            }
        })
    }

    override fun onBackPressed() {
        when (supportFragmentManager.findFragmentById(R.id.container)) {
            is AddTaskFragment -> navigationViewModel.setNavigation(TimelineNavigation)
            else -> super.onBackPressed()
        }
    }
}
