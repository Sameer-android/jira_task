package com.example.jira.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jira.MainActivity
import com.example.jira.R
import com.example.jira.adapters.TaskAdapter
import com.example.jira.dataClasses.AssignTask
import com.example.jira.databinding.FragmentOpenProjectBinding
import com.google.firebase.firestore.FirebaseFirestore

class OpenProjectFragment : Fragment() {
    private lateinit var binding: FragmentOpenProjectBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentOpenProjectBinding.inflate(inflater, container, false)
        binding.rvShowTasks.layoutManager = LinearLayoutManager(requireContext())
        // Listen for "taskAdded" event to refresh tasks
        parentFragmentManager.setFragmentResultListener("taskAdded", this) { _, _ ->
            showTask()
        }
        initUi()
        showDataFireStore()
        createTask()
        return binding.root
    }

    private fun showTask() {
        val projectId = arguments?.getString("PROJECT_ID")
        if (projectId != null) {
            db.collection("tasks")
                .whereEqualTo("projectId", projectId)
                .get()
                .addOnSuccessListener { documents ->
                    val taskList = ArrayList<AssignTask>()
                    for (document in documents) {
                        val task = document.toObject(AssignTask::class.java)
                        taskList.add(task)
                    }
                    // Update RecyclerView with the filtered tasks
                    val adapter = TaskAdapter(taskList, requireContext())
                    binding.rvShowTasks.adapter = adapter
                }
                .addOnFailureListener { e ->
                    Log.w("OpenProjectFragment", "Error getting tasks", e)
                }
        } else {
            Log.e("OpenProjectFragment", "PROJECT_ID is null")
        }
    }

    private fun createTask() {
        binding.createTasks.setOnClickListener {
            val projectId =
                requireArguments().getString("PROJECT_ID") // Retrieve the current project ID

            val openCreateTaskFragment = CreateTaskFragment().apply {
                arguments = Bundle().apply {
                    putString("PROJECT_ID", projectId)
                }
            }
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main, openCreateTaskFragment)
                .addToBackStack(null)
                .commit()

        }
    }


    private fun showDataFireStore() {
        // Retrieve PROJECT_ID from the arguments
        val projectId = arguments?.getString("PROJECT_ID")

        if (projectId != null) {
            // Fetch the data for the project using the document ID (projectId)
            db.collection("projects")
                .document(projectId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val projectName = document.getString("projectName")
                        val projectDes = document.getString("projectDes")

                        Log.d("OpenProjectFragment", "Project Name: $projectName")
                        Log.d("OpenProjectFragment", "Project Description: $projectDes")

                        binding.projectName.text = projectName
                        binding.projectDes.text = projectDes
                    } else {
                        Log.d("OpenProjectFragment", "No such document")
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("OpenProjectFragment", "Error getting document: ", e)
                }
        } else {
            Log.e("OpenProjectFragment", "PROJECT_ID is null")
        }
    }

    private fun initUi() {
        val projectId = arguments?.getString("PROJECT_ID")
        if (projectId != null) {
            Log.d("PROJECT_ID", projectId) // Log the PROJECT_ID
        } else {
            Log.e("PROJECT_ID", "PROJECT_ID is null")
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomBar(false)
        showTask()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener("taskAdded", this) { _, _ ->
            showTask() // Reload tasks
        }
    }

}