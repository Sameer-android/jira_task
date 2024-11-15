package com.example.jira.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jira.MainActivity
import com.example.jira.R
import com.example.jira.adapters.ProjectAdapter
import com.example.jira.dataClasses.AddProject
import com.example.jira.dataClasses.OnItemClickListener
import com.example.jira.databinding.FragmentShowDataBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ShowDataFragment : Fragment(), OnItemClickListener.OnItemClick {
    private var projectAdapter: ProjectAdapter? = null
    private lateinit var binding: FragmentShowDataBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowDataBinding.inflate(inflater, container, false)
        addProject()
        addRecyclerView()
        return binding.root
    }

    private fun addRecyclerView() {
        val list = arrayListOf<AddProject>()
        projectAdapter = ProjectAdapter(list, requireContext(), this@ShowDataFragment)
        binding.rvShowData.layoutManager = LinearLayoutManager(requireContext())
        binding.rvShowData.adapter = projectAdapter

        val db = Firebase.firestore
        //Read data
        db.collection("projects")
            .addSnapshotListener { result, e ->
                list.clear()
                for (document in result!!.documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val project = document.toObject(AddProject::class.java)
                    project?.id = document.id
                    list.add(project!!)
                }
                projectAdapter!!.notifyDataSetChanged()
            }
    }

    private fun addProject() {
        binding.addProject.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main, AddProjectFragment()).addToBackStack(null).commit()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomBar(true) // Shows the bottom bar
    }

    override fun onClick(position: Int, dataString: String) {
        when (dataString) {
            "PROJECTS" -> {
                gotoProjectDetailsScreen(position)
            }
        }
    }

    private fun gotoProjectDetailsScreen(position: Int) {
        val fragment = OpenProjectFragment()
        val bundle = Bundle()
        bundle.putString("PROJECT_ID", projectAdapter!!.getList()[position].id)
        fragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main, fragment)
            .addToBackStack(null)
            .commit()
    }

}