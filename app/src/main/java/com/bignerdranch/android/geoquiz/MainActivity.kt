package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0
    private var answeredQuestions = List(questionBank.size) {true}.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener {
            checkAnswer(true)
            answeredQuestions.add(currentIndex, false)
            updateButtons()
        }
        binding.falseButton.setOnClickListener {
            checkAnswer(false)
            answeredQuestions.add(currentIndex, false)
            updateButtons()
        }


        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            updateButtons()
        }
        binding.questionTextView.setOnClickListener{
            currentIndex = (currentIndex+1) % questionBank.size
            updateQuestion()
        }
        binding.prevButton.setOnClickListener{
            currentIndex = if(currentIndex>0){
                (currentIndex-1)
            }else{
                questionBank.size-1
            }
            updateQuestion()
            updateButtons()
        }



    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
    private fun updateQuestion() {

            val questionTextRestId = questionBank[currentIndex].textRestId
            binding.questionTextView.setText(questionTextRestId)

    }

    private fun updateButtons(){
        binding.trueButton.isEnabled = answeredQuestions[currentIndex]
        binding.falseButton.isEnabled = answeredQuestions[currentIndex]
    }



    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast
        }else{
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}