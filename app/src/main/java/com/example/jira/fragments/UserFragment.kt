package com.example.jira.fragments

import HomeFragment
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jira.R
import com.example.jira.adapters.RvAdapter
import com.example.jira.dataClasses.User
import com.example.jira.databinding.FragmentUserBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUserBinding.inflate(inflater, container, false)
        addRecyclerView()
        addUser()


        return binding.root
    }

    private fun addUser() {
        binding.adduser.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main, HomeFragment()).addToBackStack(null).commit()
        }
    }


    private fun addRecyclerView() {
        var list = arrayListOf<User>()
        var rvAdapter = RvAdapter(list, requireContext())
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = rvAdapter
        val db = Firebase.firestore
        //Read data
        db.collection("users")
            .addSnapshotListener { result, e ->
                list.clear()
                for (document in result!!.documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val user = document.toObject(User::class.java)
                    user?.id = document.id
                    list.add(user!!)
                }
                rvAdapter.notifyDataSetChanged()
            }
    }


}