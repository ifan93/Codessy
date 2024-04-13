package com.example.codessy.code

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codessy.databinding.CodeItemRecyclerBinding

class CodeAdapter(private val codeModelList: List<CodeModel>) :
    RecyclerView.Adapter<CodeAdapter.MyViewHolder>() {
    class MyViewHolder(private val binding: CodeItemRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CodeModel) {
            binding.apply {
                codeTitleText.text = model.codeTopic
                codeSubText.text = model.codeSubtitle
                root.setOnClickListener {
                    val intent = Intent(root.context, CodeActivity::class.java)
                    CodeActivity.CodeContentModelList = model.codeContentList
                    root.context.startActivity(intent)
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CodeItemRecyclerBinding .inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(codeModelList[position])
    }

    override fun getItemCount(): Int {
        return codeModelList.size
    }
}