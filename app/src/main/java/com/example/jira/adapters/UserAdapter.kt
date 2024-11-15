package com.example.jira.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jira.dataClasses.User
import com.example.jira.databinding.RowItemBinding

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
    }
}
