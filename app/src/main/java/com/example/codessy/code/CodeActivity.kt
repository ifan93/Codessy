package com.example.codessy.code

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.codessy.R
import com.example.codessy.databinding.ActivityCodeBinding


class  CodeActivity : AppCompatActivity(), View.OnClickListener {


    companion object {
        var CodeContentModelList: List<CodeContentModel> = listOf()
    }

    var currentCodeContentIndex = 0
    lateinit var binding: ActivityCodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            prevBtn.setOnClickListener(this@CodeActivity)
            nxtBtn.setOnClickListener(this@CodeActivity)
        }

        binding.bckBtn.setOnClickListener{
            startActivity(Intent(this,CodePage::class.java))
        }

        binding.copyButton1.setOnClickListener {
            val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("CodeSnippet", binding.codeSnippet.text)
            clipboardManager.setPrimaryClip(clip)
            Toast.makeText(this@CodeActivity, "Code snippet copied to clipboard", Toast.LENGTH_LONG).show()
        }

        binding.copyButton.setOnClickListener {
            val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("CodeSnippet2", binding.codeSnippet2.text)
            clipboardManager.setPrimaryClip(clip)
            Toast.makeText(this@CodeActivity, "Code snippet copied to clipboard", Toast.LENGTH_LONG).show()
        }

        loadContent()

    }

    private fun loadContent() {

        if (currentCodeContentIndex == CodeContentModelList.size) {

            return

        }
        val currentContent = CodeContentModelList[currentCodeContentIndex]
        binding.apply {
            codeSnipTitle.text =
                "${currentContent.codeSubTopic} ${currentCodeContentIndex + 1}/${CodeContentModelList.size}"
            questionProgress.progress =
                (currentCodeContentIndex.toFloat() / CodeContentModelList.size.toFloat() * 100).toInt()
            codeSnippet.text = CodeContentModelList[currentCodeContentIndex].sourceCode[0]
            codeSnippet2.text= CodeContentModelList[currentCodeContentIndex].sourceCode[1]
            prevBtn.visibility = if (currentCodeContentIndex > 0) View.VISIBLE else View.GONE

            if (currentCodeContentIndex == CodeContentModelList.size - 1) {
                nxtBtn.visibility = View.GONE // Hide the ImageButton
                finishButton.visibility = View.VISIBLE // Show the Button
                finishButton.setOnClickListener {
                    startActivity(Intent(this@CodeActivity, CodePage::class.java))
                }
            } else {
                nxtBtn.visibility = View.VISIBLE // Show the ImageButton
                finishButton.visibility = View.GONE // Hide the Button
            }

        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.nxtBtn -> {
                currentCodeContentIndex++
                loadContent()
            }
            R.id.prevBtn -> {
                currentCodeContentIndex--
                loadContent()
            }

        }
    }

}