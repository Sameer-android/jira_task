package com.example.jira.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jira.dataClasses.AddProject
import com.example.jira.dataClasses.AssignTask
import com.example.jira.databinding.ProjecttaskItemBinding

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

    }

    class ViewHolder(var binding: ProjecttaskItemBinding) : RecyclerView.ViewHolder(binding.root)
}
