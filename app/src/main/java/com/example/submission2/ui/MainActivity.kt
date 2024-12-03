package com.example.submission2.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.submission2.R
import com.example.submission2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        replaceFragment(UpcomingEvent())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.upcoming -> {
                    replaceFragment(UpcomingEvent())
                    Log.d("UpcomingFragment", "Pencet Upcoming")
                }
                R.id.finished -> {
                    replaceFragment(FinishedEvent())
                    Log.d("FinishedFragment", "Pencet Finished")
                }
                else -> {

                }
            }
            true
        }


    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        Log.d("ReplaceFragment", "$fragment")
        fragmentManager.commit {
            replace(R.id.frame_layout, fragment).addToBackStack(null)
        }
    }
}