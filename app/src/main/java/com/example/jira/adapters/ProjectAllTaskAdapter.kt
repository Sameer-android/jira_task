package com.example.jira.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.jira.R
import com.example.jira.dataClasses.AddProject
import com.example.jira.dataClasses.AssignTask
import com.example.jira.databinding.ProjecttaskItemBinding
import com.example.jira.fragments.EditTaskFragment

class ProjectAllTaskAdapter(
    private var projectTaskList: ArrayList<AssignTask>,
    private var context: Context,
) :
    RecyclerView.Adapter<ProjectAllTaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ProjecttaskItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return projectTaskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = projectTaskList[position]
        holder.binding.moduleAllTaskTv.text = "Module - ${items.module}"
        holder.binding.taskAllTv.text = "Task - ${items.tasks}"
        holder.binding.assignAllTv.text = "AssignTo - ${items.assignTo}"
        anim(holder.itemView)

        holder.binding.projectTaskItems.setOnClickListener {
            // Create the fragment and pass the data using arguments
            val fragment = EditTaskFragment().apply {
                arguments = Bundle().apply {
                    putString("MODULE", items.module)
                    putString("TASK", items.tasks)
                    putString("ASSIGN", items.assignTo)
                    putString("ID", items.projectId)
                    putString("task_id" , items.id)
                }
            }
            // Replace the current fragment with EditTaskFragment
            (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main, fragment) // Replace with your container ID
                .addToBackStack(null) // Optional: Add to backstack
                .commit()
        }
    }

    class ViewHolder(var binding: ProjecttaskItemBinding) : RecyclerView.ViewHolder(binding.root)
    fun anim(view: View){
        var animation= AlphaAnimation(0.0f,1.0f)
        animation.duration=1000
        view.startAnimation(animation)
    }
}
