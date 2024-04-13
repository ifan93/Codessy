package com.example.codessy.learn

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codessy.databinding.LearnItemRecyclerBinding

class LearnListAdapter(private val learnModelList: List<LearnModel>) :
    RecyclerView.Adapter<LearnListAdapter.MyViewHolder>() {
    class MyViewHolder(private val binding: LearnItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: LearnModel) {
            binding.apply {
                learnTitleText.text = model.topic
                learnSubtitleText.text= model.subtitle
                root.setOnClickListener{
                    val intent= Intent(root.context, LearnActivity::class.java)
                    LearnActivity.learnModelList= model.contentList
                    root.context.startActivity(intent)
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = LearnItemRecyclerBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(learnModelList[position])
    }

    override fun getItemCount(): Int {
        return learnModelList.size
    }
}