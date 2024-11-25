package com.example.jira.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.example.jira.dataClasses.AssignTask
import com.example.jira.databinding.TaskRvItemBinding

class TaskAdapter(private var taskList: ArrayList<AssignTask>, private var context: Context) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    class ViewHolder(var binding: TaskRvItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = TaskRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = taskList[position]
        holder.binding.moduleTv.text = "Module - ${items.module}"
        holder.binding.taskTv.text = "Task - ${items.tasks}"
        holder.binding.assignTv.text = "AssignTo - ${items.assignTo}"
        anim(holder.itemView)
    }
    fun anim(view: View){
        var animation= AlphaAnimation(0.0f,1.0f)
        animation.duration=1000
        view.startAnimation(animation)
    }
}
