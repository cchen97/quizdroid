package edu.washington.manjic.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.content.Intent

class SummaryActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val heading: TextView = findViewById(R.id.textView_topic_heading)
        heading.text = intent.extras[TOPIC] as String
        val descriptionTextView: TextView = findViewById(R.id.textView_topic_description)
        val descriptionText = when(intent.extras[TOPIC_NUM]) {
            0 -> getString(R.string.text_topic_description_1)
            1 -> getString(R.string.text_topic_description_2)
            2 -> getString(R.string.text_topic_description_3)
            else -> ""
        }

        descriptionTextView.text = descriptionText
        val currTopicNum: Int = intent.extras[TOPIC_NUM].toString().toInt() + 1
        val numQuestionsResName = "topic" + currTopicNum + "_numberOfQuestions"
        val resId = resources.getIdentifier(numQuestionsResName, "string", packageName)
        val numQuestionsTextView: TextView = findViewById(R.id.textView_topic_numberOfQuestions)
        val numQuestions = getString(resId)
        numQuestionsTextView.text = "Number of questions: " + numQuestions


        val startBtn = findViewById<Button>(R.id.button_startQuiz)
        startBtn.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
                .putExtra(QuestionActivity.TOPIC_NUM, currTopicNum)
                .putExtra(QuestionActivity.QUESTION_NUM, "0")
                .putExtra(QuestionActivity.NUM_CORRECT, "0")
                .putExtra(QuestionActivity.NUM_QUESTIONS, numQuestions)
            startActivity(intent)

        }
    }

    companion object {
        const val TOPIC = "edu.washington.manjic.quizdroid.summary.TOPIC"
        const val TOPIC_NUM = "edu.washington.manjic.quizdroid.summary.TOPIC_NUM"
        const val DESCRIPTION = "edu.washington.manjic.quizdroid.summary.DESCRIPTION"
        const val NUM_QUESTIONS = "edu.uw.ischool.manjic.QuizDroid.summary.NUM_QUESTIONS"
    }
}