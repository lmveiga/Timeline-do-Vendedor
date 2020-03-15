package com.gmail.lucasmveigabr.timelinedovendedor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.lucasmveigabr.timelinedovendedor.feature.addtask.AddTaskFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AddTaskFragment.newInstance())
                    .commitNow()
        }
    }
}
