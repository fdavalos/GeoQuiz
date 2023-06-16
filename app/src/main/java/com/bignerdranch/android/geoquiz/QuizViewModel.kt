package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val CURRENT_QUESTION_KEY = "CURRENT_QUESTION_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY)?:0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY,value)
    private var score: Int = 0


    private var answeredQuestions: MutableList<Boolean>
        get() = savedStateHandle.get<MutableList<Boolean>>(CURRENT_QUESTION_KEY)
            ?: List(questionBank.size) { true }.toMutableList()
        set(value){
            savedStateHandle.set(CURRENT_QUESTION_KEY, value)
        }

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val allQuestionsAnswered: Boolean
        get() = !answeredQuestions.contains(true)


    val scorePercentage: Double
        get() = score.toDouble()/questionBank.size*100

    fun setAnsweredQuestion(){
        answeredQuestions[currentIndex] = false
        answeredQuestions = answeredQuestions.apply {set(currentIndex, false) }
    }

    fun incrementScore(){
        score +=1

    }

    val currentAnsweredQuestion: Boolean
        get() = answeredQuestions[currentIndex]

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