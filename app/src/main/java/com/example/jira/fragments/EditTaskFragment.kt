package com.example.jira.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.jira.MainActivity
import com.example.jira.databinding.FragmentEditTaskBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditTaskFragment : Fragment() {
    private var projectId: String? = ""
    private lateinit var binding: FragmentEditTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_edit_task, container, false)
        binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        /* updateTasks()*/
        return binding.root
    }


    /*private fun updateTasks() {
        binding.updateTask.setOnClickListener {
            val module = binding.moduleEdit.text.toString()
            val tasks = binding.tasksEdit.text.toString()
            val assignTo = binding.assignToEdit.text.toString()

            if (module.isEmpty() || tasks.isEmpty() || assignTo.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all the details", Toast.LENGTH_LONG)
                    .show()
            } else{
                val db = Firebase.firestore
                val user = hashMapOf(
                    "module" to binding.moduleEdit.text.toString(),
                    "tasks" to binding.tasksEdit.text.toString(),
                    "assignTo" to binding.assignToEdit.text.toString(),
                )
                val documentId = arguments?.getString("ID")
                db.collection("users").document(documentId!!).set(user)
                    .addOnSuccessListener {
                        Toast.makeText(this,"Update Successful",Toast.LENGTH_LONG).show()
                        finish()
                    }
                    .addOnFailureListener { e->
                        Toast.makeText(this,"Update failed:$e",Toast.LENGTH_LONG).show()
                    }
            }
        }
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data from arguments
        val module = arguments?.getString("MODULE")
        val task = arguments?.getString("TASK")
        val assignTo = arguments?.getString("ASSIGN")
        val documentId = arguments?.getString("ID")
        projectId = arguments?.getString("ID")
        val taskId = arguments?.getString("task_id")

        // Set data to EditText fields
        binding.moduleEdit.setText(module)
        binding.tasksEdit.setText(task)
        binding.assignToEdit.setText(assignTo)

        binding.updateTask.setOnClickListener {
            updateTask(taskId)
        }
        binding.deleteTask.setOnClickListener {
            deleteTask(taskId)
        }
    }

    private fun deleteTask(documentId: String?) {
        val db = Firebase.firestore
        db.collection("tasks").document(documentId!!).delete()
            .addOnSuccessListener {
                Toast.makeText(context,"Deleted",Toast.LENGTH_LONG).show()
                requireActivity().supportFragmentManager.popBackStack()
            }
            .addOnFailureListener {
                Toast.makeText(context,"Deleted Failed",Toast.LENGTH_LONG).show()
            }
    }

    private fun updateTask(documentId: String?) {
        if (documentId.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Invalid document ID", Toast.LENGTH_LONG).show()
            return
        }
        val module = binding.moduleEdit.text.toString()
        val tasks = binding.tasksEdit.text.toString()
        val assignTo = binding.assignToEdit.text.toString()

        if (module.isEmpty() || tasks.isEmpty() || assignTo.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all the details", Toast.LENGTH_LONG)
                .show()
            return
        }

        val db = Firebase.firestore
        /*val taskData = hashMapOf(
            "module" to module,
            "tasks" to tasks,
            "assignTo" to assignTo,
            "projectId" to projectId
        )*/
        val taskData = mapOf(
            "module" to module,
            "tasks" to tasks,
            "assignTo" to assignTo
        )
        //db.collection("tasks").whereEqualTo("uniqueKey", uniqueKeyValue)
        db.collection("tasks").document(documentId).update(taskData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Update Successful", Toast.LENGTH_LONG).show()
                requireActivity().supportFragmentManager.popBackStack() // Go back
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Update failed: $e", Toast.LENGTH_LONG).show()
            }
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.showBottomBar(false)
    }

}