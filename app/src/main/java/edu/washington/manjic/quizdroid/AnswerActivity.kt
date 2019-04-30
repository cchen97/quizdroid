package edu.washington.manjic.quizdroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.content.Intent
import android.widget.TextView

class AnswerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        var givenAnswer = "Your answer: "
        givenAnswer += intent.extras[GIVEN_ANSWER] as String
        var correctAnswer = "Correct answer: "
        correctAnswer += intent.extras[CORRECT_ANSWER] as String
        val scoreTally = "You have answered " + intent.extras[NUM_CORRECT] + " out of " + intent.extras[QUESTION_NUM] + " correctly."

        val textViewGivenAnswer = findViewById<TextView>(R.id.textView_givenAnswer)
        textViewGivenAnswer.text = givenAnswer
        val textViewCorrectAnswer = findViewById<TextView>(R.id.textView_correctAnswer)
        textViewCorrectAnswer.text = correctAnswer
        val textViewScoreTally = findViewById<TextView>(R.id.textView_scoreTally)
        textViewScoreTally.text = scoreTally

        val continueBtn = findViewById<Button>(R.id.button_nextQuestion)
        var isFinished = false
        if (intent.extras[QUESTION_NUM].toString().toInt() >= intent.extras[NUM_QUESTIONS].toString().toInt()) {
            continueBtn.text = "Finish"
            isFinished = true
        }
        val newIntent = when(isFinished) {
            true -> Intent(this, MainActivity::class.java)
            else -> Intent(this, QuestionActivity::class.java)
                .putExtra(QuestionActivity.TOPIC_NUM, intent.extras[TOPIC_NUM].toString())
                .putExtra(QuestionActivity.QUESTION_NUM, intent.extras[QUESTION_NUM].toString())
                .putExtra(QuestionActivity.NUM_CORRECT, intent.extras[NUM_CORRECT].toString())
                .putExtra(QuestionActivity.NUM_QUESTIONS, intent.extras[NUM_QUESTIONS].toString())
        }
        continueBtn.setOnClickListener {
            startActivity(newIntent)
        }
    }

    companion object {
        const val TOPIC = "edu.uw.ischool.manjic.quizdroid.answer.TOPIC"
        const val TOPIC_NUM = "edu.washington.manjic.quizdroid.answer.TOPIC_NUM"
        const val DESCRIPTION = "edu.washington.manjic.quizdroid.answer.DESCRIPTION"
        const val QUESTION_NUM = "edu.washington.manjic.quizdroid.answer.QUESTION_NUM"
        const val NUM_CORRECT = "edu.washington.manjic.quizdroid.answer.DESCRIPTION"
        const val NUM_QUESTIONS = "edu.washington.manjic.quizdroid.answer.NUM_QUESTIONS"
        const val GIVEN_ANSWER = "edu.washington.manjic.quizdroid.answer.GIVEN_ANSWER"
        const val CORRECT_ANSWER = "edu.washington.manjic.quizdroid.answer.CORRECT_ANSWER"
    }
}

