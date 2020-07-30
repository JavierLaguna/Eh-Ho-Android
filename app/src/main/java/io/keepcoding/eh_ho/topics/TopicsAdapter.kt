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

    // Unit == Void
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
        val context = parent.context
        // AttachToRoot -> reemplaza el parent con la vista que se va a inflar
//        val view = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false)
        val view = parent.inflate(R.layout.item_topic)
        return TopicHolder(view)
    }

    override fun onBindViewHolder(holder: TopicHolder, position: Int) {
//        val topic = topics.get(position)
        val topic = topics[position]

//        holder.itemView.findViewById<TextView>(R.id.label_topic).setText(topic.title)
        holder.topic = topic

        holder.itemView.setOnClickListener(listener)
    }

    fun setTopics(topics: List<Topic>) {
        this.topics.clear()
        this.topics.addAll(topics)
        notifyDataSetChanged()
    }

    // VIewHolder
    inner class TopicHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var topic: Topic? = null
            set(value) {
                field = value
                itemView.tag = field

//                itemView.findViewById<TextView>(R.id.label_topic).text = field?.title
//                itemView.labelTitle.text = field?.title
//                itemView.labelPosts.text = field?.posts.toString()
//                itemView.labelViews.text = field?.views.toString()
//                setTimeOffset(field?.getTimeOffset())

                field?.let { topic ->
                    itemView.labelTitle.text = topic.title
                    itemView.labelPosts.text = topic.posts.toString()
                    itemView.labelViews.text = topic.views.toString()
                    setTimeOffset(topic.getTimeOffset())
                }
            }

        private fun setTimeOffset(timeOffset: Topic.TimeOffset) {
//            this.itemView.context.resources.getQuantityString()

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