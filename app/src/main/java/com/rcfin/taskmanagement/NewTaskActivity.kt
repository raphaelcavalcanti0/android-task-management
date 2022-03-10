package com.rcfin.taskmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.rcfin.taskmanagement.helpers.DbHelper
import com.rcfin.taskmanagement.models.Task


class NewTaskActivity : AppCompatActivity() {

    private var editTextAtividade : EditText? = null
    private var editTextDate : EditText? = null
    private var editTextTime : EditText? = null
    private var textViewDesc : EditText? = null
    private var buttonCancelar : Button? = null
    private var buttonSalvar : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_task_activity)

        editTextAtividade = findViewById(R.id.editTextAtividade)
        editTextDate = findViewById(R.id.editTextDate)
        editTextTime = findViewById(R.id.editTextTime)
        textViewDesc = findViewById(R.id.textViewDesc)
        buttonCancelar = findViewById(R.id.buttonCancelar)
        buttonSalvar = findViewById(R.id.buttonSalvar)

        buttonCancelar?.setOnClickListener{
            cancelar()
        }
        buttonSalvar?.setOnClickListener{
            if (intent.hasExtra("task_id")) {
                val task = Task()
                task.id = intent.getIntExtra("task_id", 0)
                task.title = editTextAtividade?.text.toString()
                task.date = editTextDate?.text.toString()
                task.time = editTextTime?.text.toString()
                task.describe = textViewDesc?.text.toString()
                val db = DbHelper(applicationContext)
                db.atualizar(task)
                finish()
            }
            else {
                salvar()
            }
        }

        if (intent.hasExtra("task_id")) {
            val task = carregar(intent.getIntExtra("task_id", 0))
            editTextAtividade?.setText(task.title)
            editTextDate?.setText(task.date)
            editTextTime?.setText(task.time)
            textViewDesc?.setText(task.describe)
        }
    }

    private fun salvar() {
        if (
            editTextAtividade?.text?.length != 0 &&
            editTextDate?.text?.length != 0 &&
            editTextTime?.text?.length != 0 &&
            textViewDesc?.text?.length != 0) {

            val task = Task()
            task.title = editTextAtividade?.text.toString()
            task.describe = textViewDesc?.text.toString()
            task.date = editTextDate?.text.toString()
            task.time = editTextTime?.text.toString()

            val db = DbHelper(applicationContext)
            db.insertTask(task)
            finish()

        } else {
            Toast.makeText(this, "É preciso preencher todos os campos.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun carregar(id: Int) : Task {
        val db = DbHelper(applicationContext)
        return db.findById(id)
    }

    private fun cancelar() {
        finish()
    }
}