package edu.washington.manjic.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log

class ControllerActivity: AppCompatActivity(), TopicFragment.OnBeginBtnPressedListener, QuizFragment.OnQuizSubmitBtnClickListener,AnswerFragment.OnContinueBtnClickedListener {
    private lateinit var currentTopic: String
    private lateinit var currentTopicData: Topic
    private var currQuestionNumber = 0
    private var correctAnswersCount = 0
    private val instance = QuizApp.getSingletonInstance()
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller)

        val  inputStream = application.assets.open("questions.json")
        val json = inputStream.bufferedReader().use{it.readText()}

        currentTopic = intent.extras[TOPIC_NAME] as String
        var fragment: Fragment? = null
        currentTopicData = instance.repository.getTopic(currentTopic)
        Log.i("aaaaaaaa", currentTopicData.getQuestions().get(0).answer1)

        if (currQuestionNumber == 0) {
            fragment = TopicFragment.newInstance(
                currentTopic, currentTopicData
            )
        }

        if (fragment != null) {
            val ft = fragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, fragment)
            ft.commit()
        }
    }

    override fun onBeginBtnPressed() {
        currQuestionNumber++

        val fragment: QuizFragment = QuizFragment.newInstance(currQuestionNumber.toString(), currentTopicData)

        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, fragment)
        ft.commit()
    }

    override fun onQuizSubmitBtnClicked(chosenAnswer: String?, correctAnwer: String?) {
        if (chosenAnswer == correctAnwer) {
            correctAnswersCount++
        }
        val fragment: AnswerFragment = AnswerFragment.newInstance(
            chosenAnswer as String,
            correctAnwer as String,
            correctAnswersCount.toString(),
            currQuestionNumber.toString(),
            currentTopicData
        )

        if (fragment != null) {
            val ft = fragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, fragment)
            ft.commit()
        }
    }

    override fun onContinueBtnClicked() {
        if (currQuestionNumber.toString() == currentTopicData.getQuestions().count().toString()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            onBeginBtnPressed()
        }
    }


    companion object {
        const val TOPIC_NAME = "edu.washington.manjic.quizdroid.ControllerActivity.TOPIC_NAME"
    }
}