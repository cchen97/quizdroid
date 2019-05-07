package edu.washington.manjic.quizdroid

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat.startActivity
import android.widget.Toast

class ControllerActivity: AppCompatActivity(), TopicFragment.OnBeginBtnPressedListener, QuizFragment.OnQuizSubmitBtnClickListener,AnswerFragment.OnContinueBtnClickedListener {
    private lateinit var currentTopic: String
    private lateinit var currentTopicData: HashMap<String, String>
    private var currQuestionNumber = 0
    private var correctAnswersCount = 0
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller)

        currentTopic = intent.extras[TOPIC_NAME] as String
        var fragment: Fragment? = null
        currentTopicData = when (intent.extras[TOPIC_NAME] as String) {
            "Math" -> topic1
            "Physics" -> topic2
            "Marvel Super Heroes" -> topic3
            else -> topic1
        }

        if (currQuestionNumber == 0) {
            fragment = TopicFragment.newInstance(
                currentTopicData["name"] as String,
                currentTopicData["description"] as String,
                currentTopicData
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
        if (currQuestionNumber.toString() == currentTopicData["numQuestions"]) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            onBeginBtnPressed()
        }
    }


    companion object {
        const val TOPIC_NAME = "edu.washington.manjic.quizdroid.ControllerActivity.TOPIC_NAME"

        private val topic1 = hashMapOf(
            "name" to "Math",
            "description" to "Yo its Math",
            "numQuestions" to "3",
            "Q1" to "Sine, cosine, and what?",
            "Q1_A1" to "Quotient",
            "Q1_A2" to "Tangent",
            "Q1_A3" to "Agent",
            "Q1_A4" to "Potient",
            "Q1_correctAnswer" to "Tangent",
            "Q2" to "36 * 7?",
            "Q2_A1" to "256",
            "Q2_A2" to "264",
            "Q2_A3" to "246",
            "Q2_A4" to "252",
            "Q2_correctAnswer" to "252",
            "Q3" to "Solve 9 + 9 / 3",
            "Q3_A1" to "6",
            "Q3_A2" to "12",
            "Q3_A3" to "9",
            "Q3_A4" to "15",
            "Q3_correctAnswer" to "12"
        )

        private val topic2 = hashMapOf(
            "name" to "Physics",
            "description" to "hated Physics",
            "numQuestions" to "2",
            "Q1" to "What was Newton's first law?",
            "Q1_A1" to "The law of motion",
            "Q1_A2" to "The law of gravity",
            "Q1_A3" to "The law of movement",
            "Q1_A4" to "The law of order",
            "Q1_correctAnswer" to "The law of motion",
            "Q2" to "What is the force that opposes the relative motion of two bodies that are in contact?",
            "Q2_A1" to "Normal Force",
            "Q2_A2" to "Magnetism",
            "Q2_A3" to "Friction",
            "Q2_A4" to "Gravity",
            "Q2_correctAnswer" to "Friction"
        )

        private val topic3 = hashMapOf(
            "name" to "Marvel Super Heroes",
            "description" to "Someone died in the End Game. Spoiler!",
            "numQuestions" to "2",
            "Q1" to "What powers Iron Man's suit?",
            "Q1_A1" to "He was bit by a robot",
            "Q1_A2" to "Arc Reactor",
            "Q1_A3" to "Unobtainium",
            "Q1_A4" to "Russian Titanium",
            "Q1_correctAnswer" to "Arc Reactor",
            "Q2" to "In the comics, who else can lift Thor's hammer?",
            "Q2_A1" to "Wade Wilson",
            "Q2_A2" to "Miles Morales",
            "Q2_A3" to "Stephen Strange",
            "Q2_A4" to "Natasha Romanoff",
            "Q2_correctAnswer" to "Natasha Romanoff"
        )
    }
}