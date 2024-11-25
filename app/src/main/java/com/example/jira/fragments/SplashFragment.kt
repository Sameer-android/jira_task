package com.example.jira.fragments

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jira.R
import com.example.jira.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)*/
        binding = FragmentSplashBinding.inflate(inflater,container,false)
        requireActivity().window.statusBarColor = Color.parseColor("#0062f7")
        startIconAndTextAnimation()
        delay()
        return binding.root
    }

    private fun delay() {
        Handler(Looper.getMainLooper()).postDelayed({
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main,LogInFragment()).commit()
        },750)
    }
    private fun startIconAndTextAnimation() {
        ObjectAnimator.ofFloat(binding.titleText, "translationY", 400f, 0f).apply {
            duration = 750
            start()
        }
    }

}