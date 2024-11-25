package com.example.jira

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.jira.databinding.FragmentUserEditBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class UserEditFragment : Fragment() {
   private lateinit var binding: FragmentUserEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_user_edit, container, false)
        binding = FragmentUserEditBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data from arguments
        val name = arguments?.getString("NAME")
        val code = arguments?.getString("CODE")
        val role = arguments?.getString("ROLE")
        val userId = arguments?.getString("id")

        binding.nameEdit.setText(name)
        binding.codeEdit.setText(code)
        binding.roleEdit.setText(role)

        binding.updateUser.setOnClickListener {
            if (userId != null) {
                updateUser(userId)
            }
        }
        binding.deleteUser.setOnClickListener {
            if (userId != null) {
                deleterUser(userId)
            }
        }
    }

    private fun deleterUser(documentId: String) {
        val db = Firebase.firestore
        db.collection("users").document(documentId!!).delete()
            .addOnSuccessListener {
                Toast.makeText(context,"Deleted",Toast.LENGTH_LONG).show()
                requireActivity().supportFragmentManager.popBackStack()
            }
            .addOnFailureListener {
                Toast.makeText(context,"Deleted Failed",Toast.LENGTH_LONG).show()
            }
    }

    private fun updateUser(documentId:String) {
        if (documentId.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Invalid document ID", Toast.LENGTH_LONG).show()
            return
        }
        val name = binding.nameEdit.text.toString()
        val code = binding.codeEdit.text.toString()
        val role = binding.roleEdit.text.toString()

        if (name.isEmpty() || code.isEmpty() || role.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all the details", Toast.LENGTH_LONG)
                .show()
            return
        }

        val db = Firebase.firestore
        val taskData = mapOf(
            "name" to name,
            "code" to code,
            "role" to role
        )
        //db.collection("tasks").whereEqualTo("uniqueKey", uniqueKeyValue)
        db.collection("users").document(documentId).update(taskData)
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