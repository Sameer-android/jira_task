package com.example.jira.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jira.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        /* return inflater.inflate(R.layout.fragment_profile, container, false)*/
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        showUserEmail()
        return binding.root
    }

    private fun showUserEmail() {
        // Get the current user from FirebaseAuth
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Get and display the user's email in a TextView
            val email = it.email
            val name = it.displayName
            binding.emailTextView.text = "User Email: $email"
            binding.nameTextView.text = "User Name: $name"
        }
    }

}