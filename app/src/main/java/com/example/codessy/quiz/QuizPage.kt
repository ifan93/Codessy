package com.example.codessy.quiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codessy.MainActivity
import com.example.codessy.databinding.ActivityQuizPageBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class QuizPage : AppCompatActivity() {
    private lateinit var binding: ActivityQuizPageBinding
    private lateinit var quizModeList: MutableList<QuizModel>
    private lateinit var adapter: QuizListAdapter
    private lateinit var dBr: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Initialize quiz mode list and fetch data
        quizModeList = mutableListOf()
        getDataQuizFromFirebase()
        binding.backBtn.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
    private fun setupRecyclerView() {
        // Set up RecyclerView using ViewBinding
        binding.progressBarQuiz.visibility=View.GONE
        binding.g.visibility = View.GONE
        adapter = QuizListAdapter(quizModeList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun getDataQuizFromFirebase() {
        binding.progressBarQuiz.visibility=View.VISIBLE

        binding.g.visibility = View.VISIBLE

        dBr = FirebaseDatabase.getInstance().getReference("Quiz")

        dBr.get().addOnSuccessListener {
            if (it.exists()) {
                for (snapshot in it.children) {
                    val quizModel=snapshot.getValue(QuizModel::class.java)
                    if(quizModel != null){
                        quizModeList.add(quizModel)
                    }


                }
                setupRecyclerView()
            }

        }
        // Dummy data




    }



}
