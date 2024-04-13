package com.example.codessy.learn

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.codessy.R
import com.example.codessy.databinding.ActivityLearnBinding


class  LearnActivity : AppCompatActivity(), View.OnClickListener {


    companion object {
        var learnModelList: List<ContentModel> = listOf()
    }

    var currentLearnContentIndex = 0
    lateinit var binding: ActivityLearnBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            prevBtn.setOnClickListener(this@LearnActivity)
            nxtBtn.setOnClickListener(this@LearnActivity)
        }
        loadContent()

    }

    private fun loadContent() {

        if (currentLearnContentIndex == learnModelList.size) {

            return

        }
        val currentContent = learnModelList[currentLearnContentIndex]
        binding.apply {
            topicIndicator.text =
                "${currentContent.learnTopic} ${currentLearnContentIndex + 1}/${learnModelList.size}"
            questionProgress.progress =
                (currentLearnContentIndex.toFloat() / learnModelList.size.toFloat() * 100).toInt()
            content1.text = learnModelList[currentLearnContentIndex].explanation[0]
            content2.text = learnModelList[currentLearnContentIndex].explanation[1]
            content3.text = learnModelList[currentLearnContentIndex].explanation[2]
            content4.text = learnModelList[currentLearnContentIndex].explanation[3]

            prevBtn.visibility = if (currentLearnContentIndex > 0) View.VISIBLE else View.GONE
            if (currentLearnContentIndex == learnModelList.size-1) {
                nxtBtn.text = "Finish"
                binding.nxtBtn.setOnClickListener{
                    startActivity(Intent(this@LearnActivity,LearnPage::class.java))
                }
            } else {
                nxtBtn.text = "Next"
            }
        }

    }

    override fun onClick(v: View?) {
        val clickedBtnN = v as Button

        if (clickedBtnN.id == R.id.nxtBtn) {
            currentLearnContentIndex++
            loadContent()

        }

        if (clickedBtnN.id == R.id.prevBtn) {
            currentLearnContentIndex--
            loadContent()

        }
    }
}