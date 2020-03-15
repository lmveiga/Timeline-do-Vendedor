package com.gmail.lucasmveigabr.timelinedovendedor.feature.timeline

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.lucasmveigabr.timelinedovendedor.R
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.TaskType
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.TaskType.*
import kotlinx.android.synthetic.main.timeline_count_holder.view.*
import java.util.*

data class TimelineCountItem(
    val type: TaskType,
    val count: Int
)

class TimelineCountAdapter(private val context: Context) :
    RecyclerView.Adapter<TimelineCountAdapter.TimelineCountHolder>() {

    var list: List<TimelineCountItem> = Collections.emptyList()
        set(value) {
            field = value.filter { it.count > 0 }.sortedByDescending { it.count }
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineCountHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.timeline_count_holder, parent, false)
        return TimelineCountHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TimelineCountHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class TimelineCountHolder(private val v: View) : RecyclerView.ViewHolder(v) {

        fun bind(item: TimelineCountItem) {
            v.countTextView.setCompoundDrawablesWithIntrinsicBounds(
                null,
                when (item.type) {
                    MAIL -> context.getDrawable(R.drawable.ic_mail)
                    CALL -> context.getDrawable(R.drawable.ic_call)
                    PROPOSAL -> context.getDrawable(R.drawable.ic_proposal)
                    MEETING -> context.getDrawable(R.drawable.ic_meeting)
                    VISIT -> context.getDrawable(R.drawable.ic_visit)
                    OTHER -> context.getDrawable(R.drawable.ic_other)
                }, null, null
            )
            v.countTextView.text = item.count.toString()
        }

    }

}