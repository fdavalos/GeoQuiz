package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.bignerdranch.android.geoquiz.R.*
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

//    private lateinit var trueButton: Button
//    private lateinit var falseButton: Button

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        trueButton = findViewById(R.id.true_button)
//        falseButton = findViewById(R.id.false_button)

//        trueButton.setOnClickListener { view: View ->
//            //Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show
//            Snackbar.make(view, R.string.correct_toast, Snackbar.LENGTH_SHORT).show()
//
//        }
//        falseButton.setOnClickListener { view: View ->
//            //Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
//            Snackbar.make(view, R.string.incorrect_toast, Snackbar.LENGTH_SHORT).show()
//        }
        binding.trueButton.setOnClickListener { view: View -> checkAnswer(true)}
        binding.falseButton.setOnClickListener { view: View -> checkAnswer(false)}

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }


    }
    private fun updateQuestion(){
        val questionTextRestId = questionBank[currentIndex].textRestId
        binding.questionTextView.setText(questionTextRestId)
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