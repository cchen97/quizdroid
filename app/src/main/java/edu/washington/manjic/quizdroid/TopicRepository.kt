package edu.washington.manjic.quizdroid

class TopicRepository {
    private  var _topics = mutableListOf<Topic>()

    fun getTopic(topicName: String): Topic {
        return (_topics.filter { it.topicTitle == topicName }).first()
    }

    fun getTopics(): List<Topic> {
        return _topics.toList()
    }

    init{
        val topic1 = Topic("Math",
            "Yo its Math.",
            "Trivia and tricky equations.")
        topic1.addQuestion(Quiz(
            "Sine, cosine, and what?",
            "Quotient",
            "Tangent",
            "Agent",
            "Potient",
            2
        ))
        topic1.addQuestion(Quiz(
            "What is 36 times 7?",
            "256",
            "264",
            "246",
            "252",
            4
        ))
        topic1.addQuestion(Quiz(
            "What does (9 + 9 / 3) resolve to?",
            "6",
            "12",
            "9",
            "15",
            2
        ))
        val topic2 = Topic("Physics",
            "Hated physics",
            "Theories, laws & Physics history")
        topic2.addQuestion(Quiz(
            "What was Newton's first law?",
            "The law of motion",
            "The law of gravity",
            "The law of movement",
            "The law of order",
            2
        ))
        topic2.addQuestion(Quiz(
            "What is the force that opposes the relative motion of two bodies that are in contact?",
            "Normal Force",
            "Magnetism",
            "Friction",
            "Gravity",
            3
        ))
        val topic3 = Topic("Marvel Super Heroes",
            "End game was nice",
            "MCU & Marvel Comics trivia")
        topic3.addQuestion(Quiz(
            "What powers Iron Man's suit?",
            "He was bit by a robot",
            "An Arc Reactor",
            "A rare Unobtainium core",
            "Pure Russian Titanium",
            2
        ))
        topic3.addQuestion(Quiz(
            "In the comics, who else can lift Thor's hammer?",
            "Wade Wilson",
            "Miles Morales",
            "Stephen Strange",
            "Natasha Romanoff",
            4
        ))
        _topics.add(topic1)
        _topics.add(topic2)
        _topics.add(topic3)
    }
}