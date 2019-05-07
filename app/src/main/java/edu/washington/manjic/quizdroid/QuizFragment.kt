package edu.washington.manjic.quizdroid

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val ARG_PARAM1 = "currQuestionNumber"

class QuizFragment: Fragment(){
    var currQuestionNumber: String? = null
    var listener: OnQuizSubmitBtnClickListener? = null
    private var _quizData: Topic? = null
    private var _selectedAnswer: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currQuestionNumber = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = (currQuestionNumber?.toInt() as Int) - 1
        val questionText = view.findViewById<TextView>(R.id.textView_question)
        val currQuestion = _quizData?.getQuestions()?.get(index)
        questionText.text = currQuestion?.questionText ?: "Oops"
//        val questionTextResName = "topic" + intent.extras[TOPIC_NUM] + "_question" + questionNumber + "_question"
//        val questionTextResId = resources.getIdentifier(questionTextResName, "string", packageName)
//        questionText.text = getString(questionTextResId)
//
        val answer1 = view.findViewById<RadioButton>(R.id.radioButton_answer1)
        answer1.text = currQuestion?.answer1 ?: "Oops"
        val answer2 = view.findViewById<RadioButton>(R.id.radioButton_answer2)
        answer2.text = currQuestion?.answer2 ?: "Oops"
        val answer3 = view.findViewById<RadioButton>(R.id.radioButton_answer3)
        answer3.text = currQuestion?.answer3 ?: "Oops"
        val answer4 = view.findViewById<RadioButton>(R.id.radioButton_answer4)
        answer4.text = currQuestion?.answer4 ?: "Oops"
//        val answer1ResName = "topic" + intent.extras[TOPIC_NUM] + "_question" + questionNumber + "_answer1"
//        val answer1ResId = resources.getIdentifier(answer1ResName, "string", packageName)
//        answer1.text = getString(answer1ResId)
//
//        val answer2 = findViewById<RadioButton>(R.id.radioButton_answer2)
//        val answer2ResName = "topic" + intent.extras[TOPIC_NUM] + "_question" + questionNumber + "_answer2"
//        val answer2ResId = resources.getIdentifier(answer2ResName, "string", packageName)
//        answer2.text = getString(answer2ResId)
//
//        val answer3 = findViewById<RadioButton>(R.id.radioButton_answer3)
//        val answer3ResName = "topic" + intent.extras[TOPIC_NUM] + "_question" + questionNumber + "_answer3"
//        val answer3ResId = resources.getIdentifier(answer3ResName, "string", packageName)
//        answer3.text = getString(answer3ResId)
//
//        val answer4 = findViewById<RadioButton>(R.id.radioButton_answer4)
//        val answer4ResName = "topic" + intent.extras[TOPIC_NUM] + "_question" + questionNumber + "_answer4"
//        val answer4ResId = resources.getIdentifier(answer4ResName, "string", packageName)
//        answer4.text = getString(answer4ResId)
//
//        val correctAnswerResName = "topic" + intent.extras[TOPIC_NUM] + "_question" + questionNumber + "_correctAnswer"
//        val correctAnswerResId = resources.getIdentifier(correctAnswerResName, "string", packageName)
//        val correctAnswer: String = getString(correctAnswerResId)
//
//
        val answerRadioBtnGroup: RadioGroup = view.findViewById(R.id.radioGroup_answersWrapper)
        val submitBtn: Button = view.findViewById(R.id.button_submitAnswer)
        submitBtn.isEnabled = false
        answerRadioBtnGroup.setOnCheckedChangeListener { _, checkedId ->
            _selectedAnswer = view.findViewById<RadioButton>(checkedId).text as String
            submitBtn.isEnabled = true
            Log.i(TAG, _selectedAnswer)
        }
        val quizCorrectAnswer = currQuestion?.getCorrectAnswer() as String
        submitBtn.setOnClickListener {
            listener?.onQuizSubmitBtnClicked(_selectedAnswer, quizCorrectAnswer)
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnQuizSubmitBtnClickListener) {
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

    interface OnQuizSubmitBtnClickListener {
        fun onQuizSubmitBtnClicked(chosenAnswer: String?, correctAnswer: String?)
    }

    companion object {
        @JvmStatic
        fun newInstance(currQuestionNumber: String, quizData: Topic) =
            QuizFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1 , currQuestionNumber)
                }

                _quizData = quizData
            }
        const val TAG = "QuizFragment"
    }
}