package com.example.codessy.quiz

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codessy.databinding.QuizItemRecyclerBinding

class QuizListAdapter(private val quizModelList: List<QuizModel>) :
    RecyclerView.Adapter<QuizListAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: QuizItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: QuizModel) {
            binding.apply {
                QuizTitleText.text = model.title
                subtitleText.text= model.subtitle
                quzTime.text=model.time + "min"
                root.setOnClickListener{
                    val intent= Intent(root.context, QuizActivity::class.java)
                    QuizActivity.questionModelList= model.questionList
                    QuizActivity.time=model.time
                    root.context.startActivity(intent)
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding= QuizItemRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,
        false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return quizModelList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(quizModelList[position])
    }
}