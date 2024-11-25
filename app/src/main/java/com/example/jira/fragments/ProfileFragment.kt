package com.example.jira.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.jira.R
import com.example.jira.dataClasses.User
import com.example.jira.databinding.FragmentProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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

        showUserDetails()
        fetchUserData()
        signOut()
        return binding.root
    }

    private fun signOut() {
        binding.signOutBtn.setOnClickListener {
            FirebaseAuth.getInstance()
                .signOut()  //If I need Every sign out Pop me Google mail so this below code.
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
            GoogleSignIn.getClient(requireActivity(), gso).signOut()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main, LogInContinueFragment()).commit()
        }
    }

    private fun fetchUserData() {
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserUid != null) {
            val db = FirebaseFirestore.getInstance()

            // Fetch document using UID
            db.collection("users").document(currentUserUid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // Extract user data directly
                        val userName = document.getString("name") ?: "N/A"
                        val userCode = document.getString("code") ?: "N/A"
                        val userRole = document.getString("role") ?: "N/A"
                        val userEmail = document.getString("email") ?: "N/A"

                        // Update UI with fetched data
                        binding.nameTextView.text = "Name: $userName"
                        binding.emailTextView.text = "Email: $userEmail"
                        binding.empCode.text = "Emp Code: $userCode"
                        binding.role.text = "Role: $userRole"
                    } else {
                        Toast.makeText(requireContext(), "User data not found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        requireContext(),
                        "Failed to fetch data: ${exception.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        } else {
            Toast.makeText(requireContext(), "User is not signed in", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadImage(photoUrl: String) {
        Glide.with(this)
            .load(photoUrl)
            .placeholder(R.drawable.profile_image) // Default image while loading
            .error(R.drawable.edit_text) // Error image if loading fails
            .circleCrop()
            .into(binding.imageView2)
    }

    private fun showUserDetails() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val photoUrl = it.photoUrl?.toString()
            // Load profile picture
            photoUrl?.let { url ->
                loadImage(url)
            } ?: Log.d("ProfileFragment", "No profile picture available.")
        }
    }


}