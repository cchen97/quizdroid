package edu.washington.manjic.quizdroid

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatViewInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.lang.RuntimeException

private const val ARG_PARAM1 = "currTopicName"
class TopicFragment  : Fragment() {
    private var currTopicName: String? = null
    var _quizData: Topic? = null
    var listener: OnBeginBtnPressedListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currTopicName = it.getString(ARG_PARAM1)
        }
    }

    override  fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_topic, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topicName: TextView = view.findViewById(R.id.textView_topic_heading)
        topicName.text = currTopicName
        val topicDescriptionView: TextView = view.findViewById(R.id.textView_topic_description)
        topicDescriptionView.text = _quizData?.topicDescription

        val totalNumOfQuestionsTextView: TextView = view.findViewById(R.id.textView_topic_numberOfQuestions)
        val numQuestionsText = _quizData?.getQuestions()?.count().toString()
        totalNumOfQuestionsTextView.text = "Number of Questions: $numQuestionsText"


        val startBtn = view.findViewById<Button>(R.id.button_startQuiz)
        startBtn.setOnClickListener {
            listener?.onBeginBtnPressed()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBeginBtnPressedListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListenr")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
    interface OnBeginBtnPressedListener {
        fun onBeginBtnPressed()
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, quizData: Topic) =
            TopicFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
                _quizData = quizData
            }
    }
}