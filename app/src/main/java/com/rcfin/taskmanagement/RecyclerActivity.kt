package com.rcfin.taskmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.LayoutDirection
import android.util.Log
import android.view.View
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rcfin.taskmanagement.adapters.TaskAdapter
import com.rcfin.taskmanagement.helpers.DbHelper
import com.rcfin.taskmanagement.models.Task

class RecyclerActivity : AppCompatActivity() {

    private var recyclerView : RecyclerView? = null
    private var textNoList : TextView? = null
    private var fab : FloatingActionButton? = null
    private var lista = mutableListOf<Task>()
    lateinit private var taskAdapter: TaskAdapter
    var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_activity)

        fetchTasks()

        linearLayoutManager = LinearLayoutManager(applicationContext)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = linearLayoutManager
        taskAdapter = TaskAdapter(lista, applicationContext)
        recyclerView?.adapter = taskAdapter

        textNoList = findViewById(R.id.textNoList)

        fab = findViewById(R.id.fab)
        fab?.setOnClickListener{
            startActivity(Intent(this, NewTaskActivity::class.java))
        }

        checkLista()
    }

    override fun onRestart() {
        super.onRestart()
        val db = DbHelper(applicationContext)
        lista.clear()
        lista.addAll(db.readTasks())
        taskAdapter.atualizar()
        checkLista()
    }

    fun fetchTasks() {
        val db = DbHelper(applicationContext)
        lista.addAll(db.readTasks())
    }

    fun checkLista() {
        if (lista.size == 0) {
            recyclerView?.visibility = View.GONE
            textNoList?.visibility = View.VISIBLE
        } else {
            recyclerView?.visibility = View.VISIBLE
            textNoList?.visibility = View.GONE
        }
    }

}