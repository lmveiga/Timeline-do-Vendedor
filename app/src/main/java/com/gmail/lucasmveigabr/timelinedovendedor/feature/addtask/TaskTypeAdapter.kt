package com.gmail.lucasmveigabr.timelinedovendedor.feature.addtask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.lucasmveigabr.timelinedovendedor.R
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.TaskType
import kotlinx.android.synthetic.main.tasktype_holder.view.*

data class TaskTypeSelector(var selected: Boolean, val type: TaskType)

class TaskTypeAdapter(private val context: Context, private val selectedItem: Int = 0) :
    RecyclerView.Adapter<TaskTypeAdapter.TaskTypeHolder>() {

    private val list = listOf(
        TaskTypeSelector(false, TaskType.MAIL),
        TaskTypeSelector(false, TaskType.CALL),
        TaskTypeSelector(false, TaskType.PROPOSAL),
        TaskTypeSelector(false, TaskType.MEETING),
        TaskTypeSelector(false, TaskType.VISIT),
        TaskTypeSelector(false, TaskType.OTHER)
    )

    init {
        setSelected(selectedItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskTypeHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.tasktype_holder, parent, false)
        return TaskTypeHolder(v)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TaskTypeHolder, position: Int) {
        holder.bind(list[position])
    }

    private fun setSelected(position: Int) {
        list.forEach { it.selected = false }
        list[position].selected = true
        notifyDataSetChanged()
    }

    fun getSelected() = list.first { it.selected }

    fun getSelectedIndex() = list.indexOfFirst { it.selected }

    inner class TaskTypeHolder(private val v: View) : RecyclerView.ViewHolder(v) {

        init {
            v.setOnClickListener {
                setSelected(adapterPosition)
            }
        }

        fun bind(taskTypeSelector: TaskTypeSelector) {
            v.imageView.isSelected = taskTypeSelector.selected
            v.imageView.setImageDrawable(
                context.getDrawable(
                    when (taskTypeSelector.type) {
                        TaskType.MAIL -> R.drawable.ic_mail
                        TaskType.CALL -> R.drawable.ic_call
                        TaskType.PROPOSAL -> R.drawable.ic_proposal
                        TaskType.MEETING -> R.drawable.ic_meeting
                        TaskType.VISIT -> R.drawable.ic_visit
                        TaskType.OTHER -> R.drawable.ic_other
                    }
                )
            )
        }

    }
}