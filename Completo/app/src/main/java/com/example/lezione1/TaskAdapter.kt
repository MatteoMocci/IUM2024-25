package com.example.lezione1

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val taskList: MutableList<Task>, var taskCount: TextView) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

        class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val task = itemView.findViewById<TextView>(R.id.tvTask)
            val check = itemView.findViewById<CheckBox>(R.id.cbTask)
            val clear = itemView.findViewById<ImageButton>(R.id.ibClear)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        updateCount()
        return TaskViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        var task = taskList[position]
        holder.check.isChecked = false
        holder.task.text = task.description
        holder.check.setOnCheckedChangeListener{
            buttonView, isChecked ->
                if (isChecked) {
                    holder.task.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    task.isCompleted = true
                    if (buttonView.isPressed) {
                        SecondActivity.ScoreData.score++
                    }
                } else {
                    holder.task.paintFlags = 0
                    task.isCompleted = false
                    if (buttonView.isPressed) {
                        if (SecondActivity.ScoreData.score - 1 > 0) {
                            SecondActivity.ScoreData.score--
                        } else {
                            SecondActivity.ScoreData.score = 0
                        }
                    }
                }
                updateCount()
                SecondActivity.ScoreData.points.text =
                    "Punti Esperienza: ${SecondActivity.ScoreData.score}"

        }

        holder.clear.setOnClickListener{
            taskList.removeAt(position)
            notifyDataSetChanged()
            updateCount()
        }

    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun updateCount(){
        val count = taskList.count { !it.isCompleted }
        taskCount.text = "$count attività mancanti"
    }


}