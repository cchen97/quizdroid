package edu.washington.manjic.quizdroid

class Topic(val topicTitle: String, val topicDescription: String, val topicShortDescription: String, val quizList: ArrayList<Quiz>) {
    private var _topicQuestions = mutableListOf<Quiz>()

    fun getQuestions(): List<Quiz> {
        return _topicQuestions.toList()
    }
}