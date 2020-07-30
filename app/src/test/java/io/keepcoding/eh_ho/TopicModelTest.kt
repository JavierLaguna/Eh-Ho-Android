package io.keepcoding.eh_ho

import io.keepcoding.eh_ho.data.Topic
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*

class TopicModelTest {
    @Test
    fun getOffset_year_isCorrect() {
        val dateToCompare: Date = formatDate("01/01/2020 10:00:00")
        val testTopic = Topic(
            title = "Test",
            content = "Content test",
            date = formatDate("01/01/2019 10:00:00")
        )

        val offset = testTopic.getTimeOffset(dateToCompare)
        assertEquals("Amount comparison", 1, offset.amount)
        assertEquals("Unit comparison", Calendar.YEAR, offset.unit)
    }

    private fun formatDate(date: String): Date {
        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        return formatter.parse(date)
            ?: throw IllegalArgumentException("Date $date has an incorrect format, try again with format dd/MM/yyyy hh:mm:ss")
    }
}