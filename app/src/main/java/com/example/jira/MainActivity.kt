package com.example.jira

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.jira.databinding.ActivityMainBinding
import com.example.jira.fragments.LogInFragment
import com.example.jira.fragments.ProfileFragment
import com.example.jira.fragments.ShowDataFragment
import com.example.jira.fragments.SplashFragment
import com.example.jira.fragments.TaskFragment
import com.example.jira.fragments.UserFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeFragment()
        showBottomBar(false)
    }

    private fun changeFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.main, SplashFragment()).commit()
        binding.bottomBarMain.setOnItemSelectedListener { pos: Int ->
            val selectedFragment = when (pos) {
                0 -> ShowDataFragment()
                1 -> TaskFragment()
                2 -> UserFragment()
                3 -> ProfileFragment()
                else -> ShowDataFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, selectedFragment)
                .commit()
            true
        }
    }

    fun showBottomBar(show: Boolean) {
        binding.bottomBarMain.visibility = if (show) View.VISIBLE else View.GONE
    }

}