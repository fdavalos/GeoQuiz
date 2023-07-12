package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle the result
        if(result.resultCode == Activity.RESULT_OK){
            quizViewModel.isCheater = result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener {
            quizViewModel.setAnsweredQuestion()
            updateButtons()
            checkAnswer(true)
        }
        binding.falseButton.setOnClickListener {
            quizViewModel.setAnsweredQuestion()
            updateButtons()
            checkAnswer(false)
        }


        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            updateButtons()
        }
        binding.questionTextView.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }
        binding.prevButton.setOnClickListener{
            quizViewModel.moveToPrev()
            updateQuestion()
            updateButtons()
        }

        binding.cheatButton.setOnClickListener{
            // Start CheatActivity
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }

        updateQuestion()
        updateButtons()

    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
       super.onResume()
        //updateQuestion()
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
            val questionTextRestId = quizViewModel.currentQuestionText
            binding.questionTextView.setText(questionTextRestId)

    }

    private fun updateButtons(){
        binding.trueButton.isEnabled = quizViewModel.currentAnsweredQuestion
        binding.falseButton.isEnabled = quizViewModel.currentAnsweredQuestion
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if(quizViewModel.isCheater) {
            R.string.judgment_toast
        }else if (userAnswer == correctAnswer){
            quizViewModel.incrementScore()
            R.string.correct_toast
        }else{
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        if (quizViewModel.allQuestionsAnswered){
            val percentage = quizViewModel.scorePercentage
            val roundedPercentage = "%.2f".format(percentage) +'%'
            Toast.makeText(this, roundedPercentage, Toast.LENGTH_LONG).show()
        }
    }
}