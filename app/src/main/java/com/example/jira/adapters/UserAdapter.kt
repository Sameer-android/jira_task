package com.example.jira.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.jira.R
import com.example.jira.UserEditFragment
import com.example.jira.dataClasses.User
import com.example.jira.databinding.RowItemBinding
import com.example.jira.fragments.EditTaskFragment

class RvAdapter(private var list: ArrayList<User>, private var context: Context) :
    RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    class ViewHolder(var binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = RowItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = list[position]
        holder.binding.nameTextView.text = "Name - ${items.name}"
        holder.binding.codeTextView.text = "Code - ${items.code}"
        holder.binding.roleTextView.text = "Role - ${items.role}"
        anim(holder.itemView)

        holder.binding.userRowItems.setOnClickListener {
            // Create the fragment and pass the data using arguments
            val fragment = UserEditFragment().apply {
                arguments = Bundle().apply {
                    putString("NAME", items.name)
                    putString("CODE", items.code)
                    putString("ROLE", items.role)
                    putString("id",items.id)

                }
            }

            // Replace the current fragment with EditTaskFragment
            (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main, fragment) // Replace with your container ID
                .addToBackStack(null) // Optional: Add to backstack
                .commit()
        }
    }
    fun anim(view: View){
        var animation= AlphaAnimation(0.0f,1.0f)
        animation.duration=1000
        view.startAnimation(animation)
    }
}
