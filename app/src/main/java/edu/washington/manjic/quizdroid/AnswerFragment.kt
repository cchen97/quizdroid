package edu.washington.manjic.quizdroid

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.app.Fragment
import android.widget.TextView

private const val ARG_PARAM1 = "givenAnswer"
private const val ARG_PARAM2 = "correctAnswer"
private const val ARG_PARAM3 = "totalCorrectAnswers"
private const val ARG_PARAM4 = "currQuestionNum"
class AnswerFragment: Fragment(){

    var givenAnswer: String? = null
    var correctAnswer: String? = null
    var totalCorrectAnswers: String? = null
    var currQuestionNum: String? = null
    private var _quizData: HashMap<String, String>? = null
    var listener: OnContinueBtnClickedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            givenAnswer = it.getString(ARG_PARAM1)
            correctAnswer = it.getString(ARG_PARAM2)
            totalCorrectAnswers = it.getString(ARG_PARAM3)
            currQuestionNum = it.getString(ARG_PARAM4)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_answer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
        val textViewGivenAnswer = view.findViewById<TextView>(R.id.textView_givenAnswer)

        textViewGivenAnswer.text = "Your answer: " + givenAnswer
        val textViewCorrectAnswer = view.findViewById<TextView>(R.id.textView_correctAnswer)
        textViewCorrectAnswer.text = "Correct answer " + correctAnswer
        val textViewScoreTally = view.findViewById<TextView>(R.id.textView_scoreTally)
        textViewScoreTally.text = "You have $totalCorrectAnswers of $currQuestionNum correct."

//
        val continueBtn = view.findViewById<Button>(R.id.button_nextQuestion)
        val totalNumberQuestions: String? = _quizData?.get("numQuestions")
        if (currQuestionNum == totalNumberQuestions) {
            continueBtn.text = getString(R.string.button_answer_finishQuiz)
        }
        continueBtn.setOnClickListener {
            listener?.onContinueBtnClicked()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnContinueBtnClickedListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        _quizData = null
    }

    interface OnContinueBtnClickedListener {
        fun onContinueBtnClicked()
    }
//        var isFinished = false
//        if (intent.extras[QUESTION_NUM].toString().toInt() >= intent.extras[NUM_QUESTIONS].toString().toInt()) {
//            continueBtn.text = "Finish"
//            isFinished = true
//        }
//        val newIntent = when(isFinished) {
//            true -> Intent(this, MainActivity::class.java)
//            else -> Intent(this, QuestionActivity::class.java)
//                .putExtra(QuestionActivity.TOPIC_NUM, intent.extras[TOPIC_NUM].toString())
//                .putExtra(QuestionActivity.QUESTION_NUM, intent.extras[QUESTION_NUM].toString())
//                .putExtra(QuestionActivity.NUM_CORRECT, intent.extras[NUM_CORRECT].toString())
//                .putExtra(QuestionActivity.NUM_QUESTIONS, intent.extras[NUM_QUESTIONS].toString())
//        }
//        continueBtn.setOnClickListener {
//            startActivity(newIntent)
//        }
//    }
//
    companion object {
        @JvmStatic
        fun newInstance(givenAnswer: String, correctAnswer: String, totalCorrectAnswers: String,
                        currQuestionNum: String, quizData: HashMap<String, String>) =
                AnswerFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, givenAnswer)
                        putString(ARG_PARAM2, correctAnswer)
                        putString(ARG_PARAM3, totalCorrectAnswers)
                        putString(ARG_PARAM4, currQuestionNum)
                    }
                    _quizData = quizData
                }
    }
}

