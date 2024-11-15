package com.example.jira.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.jira.MainActivity
import com.example.jira.R
import com.example.jira.databinding.FragmentAddProjectBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddProjectFragment : Fragment() {
    private lateinit var binding: FragmentAddProjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddProjectBinding.inflate(inflater, container, false)
        addDataFireStore()
        return binding.root
    }

    private fun addDataFireStore() {
        binding.createPro.setOnClickListener {
            val projectName = binding.projectEdit.text.toString().trim()
            val projectDes = binding.desEdit.text.toString().trim()

            if (projectName.isEmpty() || projectDes.isEmpty()) {
                Toast.makeText(requireContext(), "Please Fill All The Details", Toast.LENGTH_LONG)
                    .show()
            } else {
                val db = Firebase.firestore
                val user = hashMapOf(
                    "projectName" to binding.projectEdit.text.toString(),
                    "projectDes" to binding.desEdit.text.toString()
                )
                // Add a new document with a generated ID
                db.collection("projects")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(
                            requireContext(),
                            "DocumentSnapshot added with ID: ${documentReference.id}",
                            Toast.LENGTH_LONG
                        ).show()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.main, ShowDataFragment()).commit()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            requireContext(),
                            "Error: ${e.localizedMessage}",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("fasofhaf", "${e.localizedMessage}")
                    }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomBar(false)
    }


}