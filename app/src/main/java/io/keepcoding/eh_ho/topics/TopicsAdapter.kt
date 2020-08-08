package io.keepcoding.eh_ho.topics

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.item_topic.view.*
import java.util.*

class TopicsAdapter(val topicClickListener: ((Topic) -> Unit)?) :
    RecyclerView.Adapter<TopicsAdapter.TopicHolder>() {

    private val topics = mutableListOf<Topic>()

    private val listener: ((View) -> Unit) = {
        if (it.tag is Topic) {
            val topic: Topic = it.tag as Topic
            topicClickListener?.invoke(topic)
        }
    }

    override fun getItemCount(): Int {
        return topics.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicHolder {
        val view = parent.inflate(R.layout.item_topic)
        return TopicHolder(view)
    }

    override fun onBindViewHolder(holder: TopicHolder, position: Int) {
        val topic = topics[position]

        holder.topic = topic
        holder.itemView.setOnClickListener(listener)
    }

    fun setTopics(topics: List<Topic>) {
        this.topics.clear()
        this.topics.addAll(topics)
        notifyDataSetChanged()
    }

    // ViewHolder
    inner class TopicHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var topic: Topic? = null
            set(value) {
                field = value
                itemView.tag = field

                field?.let { topic ->
                    itemView.labelTitle.text = topic.title
                    itemView.labelPosts.text = topic.posts.toString()
                    itemView.labelViews.text = topic.views.toString()
                    setTimeOffset(topic.getTimeOffset())
                }
            }

        private fun setTimeOffset(timeOffset: Topic.TimeOffset) {

            val quantityString = when (timeOffset.unit) {
                Calendar.YEAR -> R.plurals.years
                Calendar.MONTH -> R.plurals.months
                Calendar.DAY_OF_MONTH -> R.plurals.days
                Calendar.HOUR -> R.plurals.hours
                else -> R.plurals.minutes
            }

            if (timeOffset.amount == 0) {
                itemView.labelDate.text = itemView.context.getString(R.string.minutes_zero)
            } else {
                itemView.labelDate.text = itemView.context.resources.getQuantityString(
                    quantityString,
                    timeOffset.amount,
                    timeOffset.amount
                )
            }
        }
    }
}