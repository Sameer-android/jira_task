package com.example.jira.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.example.jira.dataClasses.AddProject
import com.example.jira.dataClasses.OnItemClickListener
import com.example.jira.databinding.RvItemBinding

class ProjectAdapter(
    private var projectList: ArrayList<AddProject>,
    private var context: Context,
    var onClick: OnItemClickListener.OnItemClick
) :
    RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = RvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = projectList[position]
        holder.binding.projectName.text = items.projectName
        anim(holder.itemView)

        holder.binding.projectName.setOnClickListener {
            onClick.onClick(position, "PROJECTS")
        }
    }

    fun getList(): ArrayList<AddProject> {
        return projectList
    }

    class ViewHolder(var binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root)
    fun anim(view: View){
        var animation= AlphaAnimation(0.0f,1.0f)
        animation.duration=1000
        view.startAnimation(animation)
    }
}
