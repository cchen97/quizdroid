package edu.washington.manjic.quizdroid

class Quiz(val questionText: String,
           val answer1: String, val answer2: String,
           val answer3: String, val answer4: String,
           private val correctAnswer: Int) {

    fun getCorrectAnswer(): String {
        return when(correctAnswer) {
            1 -> answer1
            2 -> answer2
            3 -> answer3
            4 -> answer4
            else -> {
                "ERROR: NO_CORRECT_ANSWER"
            }
        }
    }
}