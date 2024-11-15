package com.example.jira.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.jira.R
import com.example.jira.databinding.FragmentCreateTaskBinding
import com.google.firebase.firestore.FirebaseFirestore

class CreateTaskFragment : Fragment() {
    private lateinit var binding: FragmentCreateTaskBinding
    private val db = FirebaseFirestore.getInstance()
    private var userList: MutableList<String> = mutableListOf() // Initialize as empty list

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTaskBinding.inflate(inflater, container, false)
        fetchDataFromFirestore()
        addDataFireStore()

        return binding.root
    }

    private fun addDataFireStore() {
        binding.submitTask.setOnClickListener {
            val module = binding.module.text.toString()
            val tasks = binding.tasks.text.toString()
            val assignTo = binding.assignTo.text.toString()

            if (module.isEmpty() || tasks.isEmpty() || assignTo.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all the details", Toast.LENGTH_LONG)
                    .show()
            } else {
                val projectId = arguments?.getString("PROJECT_ID")
                if (projectId != null) {
                    val taskData = hashMapOf(
                        "module" to module,
                        "tasks" to tasks,
                        "assignTo" to assignTo,
                        "projectId" to projectId
                    )

                    db.collection("tasks")
                        .add(taskData)
                        .addOnSuccessListener {
                            Toast.makeText(
                                requireContext(),
                                "Task created successfully",
                                Toast.LENGTH_LONG
                            ).show()
                            parentFragmentManager.setFragmentResult("taskAdded", Bundle())

                            // Navigate back to OpenProjectFragment and pass the projectId
                            val openProjectFragment = OpenProjectFragment().apply {
                                arguments = Bundle().apply {
                                    putString("PROJECT_ID", projectId)
                                }
                            }
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.main, openProjectFragment)
                                .commit()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                requireContext(),
                                "Error: ${e.localizedMessage}",
                                Toast.LENGTH_LONG
                            ).show()
                            Log.e("CreateTaskFragment", "Error creating task", e)
                        }
                } else {
                    Toast.makeText(requireContext(), "Project ID is missing", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun fetchDataFromFirestore() {
        db.collection("users")
            .get()
            .addOnSuccessListener { documents ->
                userList.clear()
                for (document in documents) {
                    val userName = document.getString("name")
                    if (userName != null) {
                        userList.add(userName)
                    }
                }
                Log.d("CreateTaskFragment", "Fetched user list: $userList")

                setupDropdown()
            }
            .addOnFailureListener { e ->
                Log.w("CreateTaskFragment", "Error getting users: ", e)
            }
    }

    private fun setupDropdown() {
        if (userList.isEmpty()) {
            Toast.makeText(requireContext(), "User list is empty", Toast.LENGTH_SHORT).show()
            return
        }

        binding.assignTo.setOnClickListener {
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, userList)
            val listPopupWindow = ListPopupWindow(requireContext())
            listPopupWindow.anchorView = binding.assignTo
            listPopupWindow.setAdapter(adapter)
            listPopupWindow.setOnItemClickListener { _, _, position, _ ->
                binding.assignTo.setText(userList[position])
                listPopupWindow.dismiss()
            }
            listPopupWindow.show()
        }
    }
}