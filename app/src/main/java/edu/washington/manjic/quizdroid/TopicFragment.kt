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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class TopicFragment  : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    var _quizData: HashMap<String, String>? = null
    var listener: OnBeginBtnPressedListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override  fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_topic, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topicName: TextView = view.findViewById(R.id.textView_topic_heading)
        topicName.text = param1
        val topicDescriptionView: TextView = view.findViewById(R.id.textView_topic_description)
        topicDescriptionView.text = param2

//        val currTopicNum: Int = intent.extras[TOPIC_NUM].toString().toInt() + 1
//        val numQuestionsResName = "topic" + currTopicNum + "_numberOfQuestions"
//        val resId = resources.getIdentifier(numQuestionsResName, "string", packageName)
        val totalNumOfQuestionsTextView: TextView = view.findViewById(R.id.textView_topic_numberOfQuestions)
        val numQuestionsText = "Number of Questions: " + (_quizData?.get("numQuestions") ?: "who knows!")
        totalNumOfQuestionsTextView.text = numQuestionsText


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
        fun newInstance(param1: String, param2: String, quizData: HashMap<String, String>) =
            TopicFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
                _quizData = quizData
            }
    }
}