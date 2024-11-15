package com.example.jira.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jira.adapters.ProjectAllTaskAdapter
import com.example.jira.dataClasses.AssignTask
import com.example.jira.databinding.FragmentTaskBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding
    private var projectAllTaskAdapter: ProjectAllTaskAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTaskBinding.inflate(inflater, container, false)
        addRecyclerView()
        return binding.root
    }

    private fun addRecyclerView() {
        val list = arrayListOf<AssignTask>()
        projectAllTaskAdapter = ProjectAllTaskAdapter(list, requireContext())
        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasks.adapter = projectAllTaskAdapter

        val db = Firebase.firestore
        //Read data
        db.collection("tasks")
            .addSnapshotListener { result, e ->
                list.clear()
                for (document in result!!.documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val tasks = document.toObject(AssignTask::class.java)
//                    tasks?.id = document.id
                    list.add(tasks!!)
                }
                projectAllTaskAdapter!!.notifyDataSetChanged()
            }
    }

}