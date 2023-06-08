package com.bignerdranch.android.geoquiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0
    private var score: Int = 0
    private var answeredQuestions = List(questionBank.size) {true}.toMutableList()

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val allQuestionsAnswered: Boolean
        get() = !answeredQuestions.contains(true)

    val scorePercentage: Double
        get() = score.toDouble()/questionBank.size*100

    fun setAnsweredQuestion(){
        answeredQuestions[currentIndex] = false;
    }

    fun incrementScore(){
        score +=1
    }

    val currentAnsweredQuestion: Boolean
        get() = answeredQuestions.get(currentIndex)

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textRestId

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev(){
        if (currentIndex == 0){
            currentIndex = questionBank.size-1
        }else{
            currentIndex -= 1
        }
    }
}