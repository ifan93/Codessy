package com.example.codessy.code

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codessy.MainActivity
import com.example.codessy.databinding.ActivityCodePageBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CodePage : AppCompatActivity() {

    private lateinit var adapter: CodeAdapter
    private lateinit var binding: ActivityCodePageBinding
    private lateinit var codeModelList: MutableList<CodeModel>
    private lateinit var dBr: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        codeModelList = mutableListOf()
        getDataCodeFromFirebase()
        binding.backBtn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun getDataCodeFromFirebase() {
        binding.progressBarCode.visibility = View.VISIBLE
        binding.g.visibility = View.VISIBLE
        // Dummy data

        dBr = FirebaseDatabase.getInstance().getReference("Code")

        dBr.get().addOnSuccessListener {
            if (it.exists()) {
                for (snapshot in it.children) {
                    val codeModel=snapshot.getValue(CodeModel::class.java)
                    if(codeModel != null){
                        codeModelList.add(codeModel)
                    }


                }
                setupRecyclerView()
            }

        }
    }


    private fun setupRecyclerView() {
        binding.progressBarCode.visibility = View.GONE
        binding.g.visibility = View.GONE
        // Set up RecyclerView using ViewBinding
        adapter = CodeAdapter(codeModelList)
        binding.recyclerView3.layoutManager = LinearLayoutManager(this)
        binding.recyclerView3.adapter = adapter
    }
}