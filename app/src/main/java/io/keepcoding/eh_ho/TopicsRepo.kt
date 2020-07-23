package io.keepcoding.eh_ho

// Singleton
object TopicsRepo {
    val topics: MutableList<Topic> = mutableListOf()
        get() {
            if (field.isEmpty()) {
                field.addAll(getDummyTopics())
            }
            return field
        }

//    fun getDummyTopics() {
//        for (i in 0..10) {
//            val topic = Topic(title = "Topic $i", content = "Content $i")
//            this.topics.add(topic)
//        }
//    }

//    fun getDummyTopics(count: Int = 10): List<Topic> {
//        return (0..count).map { Topic(title = "Topic $it", content = "Content $it") }
//    }

    fun getDummyTopics(count: Int = 10) =
        (0..count).map { Topic(title = "Topic $it", content = "Content $it") }

    fun getTopic(topicId: String): Topic? = topics.find { it.id == topicId }
}