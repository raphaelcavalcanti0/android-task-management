package com.rcfin.taskmanagement.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.rcfin.taskmanagement.NewTaskActivity
import com.rcfin.taskmanagement.R
import com.rcfin.taskmanagement.helpers.DbHelper
import com.rcfin.taskmanagement.models.Task

class TaskAdapter(var list: MutableList<Task>, var context: Context) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun atualizar() {
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var itemCard : CardView? = itemView.findViewById(R.id.card)
        private var itemTitle : TextView? = itemView.findViewById(R.id.cardTitulo)
        private var itemDate : TextView? = itemView.findViewById(R.id.cardData)
        private var itemTime : TextView? = itemView.findViewById(R.id.cardTime)
        private var itemMenu : ImageView? = itemView.findViewById(R.id.moremenu)

        fun bind(item: Task) {
            itemTitle?.text = item.title
            itemDate?.text = item.date
            itemTime?.text = item.time
            itemMenu?.setOnClickListener{
                val db = DbHelper(this@TaskAdapter.context)
                db.deleteTask(item.id)
                list.remove(item)
                this@TaskAdapter.atualizar()
            }
            itemCard?.setOnClickListener{
                val intent = Intent(this@TaskAdapter.context, NewTaskActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("task_id", item.id)
                context.startActivity(intent)
            }
        }
    }
}