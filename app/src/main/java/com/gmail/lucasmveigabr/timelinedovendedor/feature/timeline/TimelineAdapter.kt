package com.gmail.lucasmveigabr.timelinedovendedor.feature.timeline

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.lucasmveigabr.timelinedovendedor.R
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Task
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.TaskType.*
import kotlinx.android.synthetic.main.task_holder.view.*
import java.text.SimpleDateFormat
import java.util.*

class TimelineAdapter(private val context: Context) :
    RecyclerView.Adapter<TimelineAdapter.TimelineHolder>() {

    var list: List<Task> = Collections.emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.task_holder, parent)
        return TimelineHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TimelineHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class TimelineHolder(private val v: View) : RecyclerView.ViewHolder(v) {

        fun bind(task: Task) {
            v.taskTypeImageView.setImageDrawable(
                when (task.type) {
                    EMAIL -> context.getDrawable(R.drawable.ic_mail)
                    CALL -> context.getDrawable(R.drawable.ic_call)
                    PROPOSAL -> context.getDrawable(R.drawable.ic_proposal)
                    MEETING -> context.getDrawable(R.drawable.ic_meeting)
                    VISIT -> context.getDrawable(R.drawable.ic_visit)
                    OTHER -> context.getDrawable(R.drawable.ic_other)
                }
            )
            v.taskTextView.text = task.description
            v.customerTextView.text = task.customer
            v.dayOfWeekTextView.text = SimpleDateFormat("E", Locale("pt", "BR")).format(task.date)
            v.timeTextView.text = SimpleDateFormat("HH:mm", Locale("pt", "BR")).format(task.date)
        }

    }

}