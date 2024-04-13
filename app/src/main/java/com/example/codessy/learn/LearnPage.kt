package com.example.codessy.learn

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codessy.MainActivity
import com.example.codessy.databinding.ActivityLearnPageBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class LearnPage : AppCompatActivity() {

    private lateinit var adapter: LearnListAdapter
    private lateinit var binding: ActivityLearnPageBinding
    private lateinit var learnModelList: MutableList<LearnModel>
    private lateinit var dBr: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        learnModelList = mutableListOf()
        getDataLearnFromFirebase()
        binding.backBtn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        binding.progressBarLearn.visibility = View.GONE
        binding.g.visibility = View.GONE
        // Set up RecyclerView using ViewBinding
        adapter = LearnListAdapter(learnModelList)
        binding.recyclerView2.layoutManager = LinearLayoutManager(this)
        binding.recyclerView2.adapter = adapter
    }

    private fun getDataLearnFromFirebase() {
        binding.progressBarLearn.visibility = View.VISIBLE
        binding.g.visibility = View.VISIBLE


        dBr = FirebaseDatabase.getInstance().getReference("Learn")

        dBr.get().addOnSuccessListener {
            if (it.exists()) {
                for (snapshot in it.children) {
                    val learnModel = snapshot.getValue(LearnModel::class.java)
                    if (learnModel != null) {
                        learnModelList.add(learnModel)
                    }


                }
                setupRecyclerView()
            }

        }


    }


}