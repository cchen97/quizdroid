package edu.washington.manjic.quizdroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import android.content.Intent

class QuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        val questionNumber = intent.extras[QUESTION_NUM].toString().toInt() + 1

        val questionText = findViewById<TextView>(R.id.textView_question)
        val questionTextResName = "topic" + intent.extras[TOPIC_NUM] + "_question" + questionNumber + "_question"
        val questionTextResId = resources.getIdentifier(questionTextResName, "string", packageName)
        questionText.text = getString(questionTextResId)

        val answer1 = findViewById<RadioButton>(R.id.radioButton_answer1)
        val answer1ResName = "topic" + intent.extras[TOPIC_NUM] + "_question" + questionNumber + "_answer1"
        val answer1ResId = resources.getIdentifier(answer1ResName, "string", packageName)
        answer1.text = getString(answer1ResId)

        val answer2 = findViewById<RadioButton>(R.id.radioButton_answer2)
        val answer2ResName = "topic" + intent.extras[TOPIC_NUM] + "_question" + questionNumber + "_answer2"
        val answer2ResId = resources.getIdentifier(answer2ResName, "string", packageName)
        answer2.text = getString(answer2ResId)

        val answer3 = findViewById<RadioButton>(R.id.radioButton_answer3)
        val answer3ResName = "topic" + intent.extras[TOPIC_NUM] + "_question" + questionNumber + "_answer3"
        val answer3ResId = resources.getIdentifier(answer3ResName, "string", packageName)
        answer3.text = getString(answer3ResId)

        val answer4 = findViewById<RadioButton>(R.id.radioButton_answer4)
        val answer4ResName = "topic" + intent.extras[TOPIC_NUM] + "_question" + questionNumber + "_answer4"
        val answer4ResId = resources.getIdentifier(answer4ResName, "string", packageName)
        answer4.text = getString(answer4ResId)

        val correctAnswerResName = "topic" + intent.extras[TOPIC_NUM] + "_question" + questionNumber + "_correctAnswer"
        val correctAnswerResId = resources.getIdentifier(correctAnswerResName, "string", packageName)
        val correctAnswer: String = getString(correctAnswerResId)


        val answerRadioBtnGroup: RadioGroup = findViewById(R.id.radioGroup_answersWrapper)
        val submitBtn: Button = findViewById(R.id.button_submitAnswer)
        submitBtn.isEnabled = false

        answerRadioBtnGroup.setOnCheckedChangeListener { _, _ ->
            submitBtn.isEnabled = true
        }

        submitBtn.setOnClickListener {
            val selectedButtonIndex = answerRadioBtnGroup.checkedRadioButtonId
            val selectedAnswer = findViewById<RadioButton>(selectedButtonIndex)
            val nextIntent = Intent(this, AnswerActivity::class.java)
                .putExtra(AnswerActivity.TOPIC_NUM, intent.extras[TOPIC_NUM].toString())
                .putExtra(AnswerActivity.QUESTION_NUM, questionNumber.toString())
                .putExtra(AnswerActivity.NUM_CORRECT, intent.extras[NUM_CORRECT].toString())
                .putExtra(AnswerActivity.NUM_QUESTIONS, intent.extras[NUM_QUESTIONS].toString())
                .putExtra(AnswerActivity.GIVEN_ANSWER, selectedAnswer.text)
                .putExtra(AnswerActivity.CORRECT_ANSWER, correctAnswer)

            if (selectedAnswer.text == correctAnswer) {
                var numCorrect = intent.extras[NUM_CORRECT].toString()
                var newNumCorrect = numCorrect.toInt() + 1
                nextIntent.putExtra(AnswerActivity.NUM_CORRECT, newNumCorrect)
            }

            startActivity(nextIntent)
        }
    }

    companion object {
        const val TOPIC = "edu.uw.ischool.manjic.quizdroid.question.TOPIC"
        const val TOPIC_NUM = "edu.uw.ischool.manjic.quizdroid.question.TOPIC_NUM"
        const val QUESTION_NUM = "edu.uw.ischool.manjic.quizdroid.question.QUESTION_NUM"
        const val NUM_CORRECT = "edu.uw.ischool.manjic.quizdroid.question.DESCRIPTION"
        const val NUM_QUESTIONS = "edu.uw.ischool.manjic.quizdroid.question.NUM_QUESTIONS"
    }
}