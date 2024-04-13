package com.example.codessy.quiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.codessy.R
import com.example.codessy.databinding.ActivityQuizBinding
import com.example.codessy.databinding.ScoreDialogBinding

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    companion object {

        var  questionModelList: List<Questionmodel> = listOf()
        var time: String = ""

    }

    var currentQuestionIndex = 0
    var selectedAnswer= ""
    var score= 0

    lateinit var binding: ActivityQuizBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btn1.setOnClickListener(this@QuizActivity)
            btn2.setOnClickListener(this@QuizActivity)
            btn3.setOnClickListener(this@QuizActivity)
            btn4.setOnClickListener(this@QuizActivity)
            nxtBtn.setOnClickListener(this@QuizActivity)
        }
        loadQuestion()
        startTimer()
    }

    private fun startTimer() {
        val totalTimeInMillis = time.toInt() * 60 * 1000L

        object : CountDownTimer(totalTimeInMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds=millisUntilFinished/1000
                val minutes= seconds /60
                val remainingSecond= seconds % 60
                binding.timerIndicator.text=String.format("%02d:%02d",minutes,remainingSecond)
            }

            override fun onFinish() {
                //finish the quiz
            }

        }.start()


    }


    @SuppressLint("SetTextI18n")
    fun loadQuestion() {

        if(currentQuestionIndex== questionModelList.size){
            finishQuiz()
            return

        }
        binding.apply {
            questionIndicator.text =
                "Question ${currentQuestionIndex + 1}/${questionModelList.size}"
            questionProgress.progress =
                (currentQuestionIndex.toFloat() / questionModelList.size.toFloat() * 100).toInt()
            questionTv.text = questionModelList[currentQuestionIndex].question
            btn1.text = questionModelList[currentQuestionIndex].options[0]
            btn2.text = questionModelList[currentQuestionIndex].options[1]
            btn3.text = questionModelList[currentQuestionIndex].options[2]
            btn4.text = questionModelList[currentQuestionIndex].options[3]
        }

    }

    override fun onClick(v: View?) {

        binding.apply {
            btn1.setBackgroundColor(getColor(R.color.popel))
            btn2.setBackgroundColor(getColor(R.color.popel))
            btn3.setBackgroundColor(getColor(R.color.popel))
            btn4.setBackgroundColor(getColor(R.color.popel))
        }
        val clickedBtn = v as Button
        if(clickedBtn.id== R.id.nxtBtn){

            if(selectedAnswer.isEmpty()){
                showEmptyAnswerDialog()
                return

            }

            if(selectedAnswer== questionModelList[currentQuestionIndex].correct){
                score++
                Log.i("score of quiz",score.toString())

            }
            currentQuestionIndex++
            loadQuestion()
        }
        else{
            selectedAnswer=clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.light_gray))

        }
    }

    private fun finishQuiz(){
        val totalQuestion= questionModelList.size
        val percentage=((score.toFloat()/totalQuestion.toFloat())*100).toInt()
        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgress.progress=percentage
            scoreProgressText.text="$percentage%"
            if (percentage>60){
                scoreTitle.text="Congrats! you have passed"

            }
            else
            {
                scoreTitle.text="Ooops! you have failed"

            }
            scoreSubTitle.text="$score/$totalQuestion are correct"

            finishBtn.setOnClickListener {
                finish()
            }
        }

        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()



    }
    private fun showEmptyAnswerDialog() {
        AlertDialog.Builder(this)
            .setTitle("Empty Answer")
            .setMessage("Please select an answer before proceeding.")
            .setPositiveButton("OK", null)
            .show()
    }
}