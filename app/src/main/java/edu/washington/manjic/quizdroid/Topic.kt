package edu.washington.manjic.quizdroid

class Topic(val topicTitle: String, val topicDescription: String, val topicShortDescription: String) {
    private var _topicQuestions = mutableListOf<Quiz>()

    fun addQuestion(newQuestion: Quiz): Boolean {
        return if (!_topicQuestions.contains(newQuestion)) {
            _topicQuestions.add(newQuestion)
            true
        } else {
            false
        }
    }

    fun getQuestions(): List<Quiz> {
        return _topicQuestions.toList()
    }
}