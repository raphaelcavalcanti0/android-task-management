package com.rcfin.taskmanagement.helpers

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.rcfin.taskmanagement.adapters.TaskAdapter
import com.rcfin.taskmanagement.models.Task

const val DATABASE_NAME : String = "taskmanagement.db"
const val TABLE_NAME : String = "tasks"
const val COL_TITLE : String = "title"
const val COL_DESCRIBE : String = "desc"
const val COL_DATE : String = "date"
const val COL_TIME : String = "time"
const val COL_ID : String = "id"


class DbHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " TEXT, " +
                COL_DATE + " TEXT, " +
                COL_TIME + " TEXT, " +
                COL_DESCRIBE + " TEXT);"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertTask(task : Task) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_TITLE, task.title)
        cv.put(COL_DATE, task.date)
        cv.put(COL_TIME, task.time)
        cv.put(COL_DESCRIBE, task.describe)

        val result = db.insert(TABLE_NAME, null, cv)
        if (result == (-1).toLong())
            Toast.makeText(context, "Falha ao criar tarefa!", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Nova tarefa criada!", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("Range")
    fun readTasks() : MutableList<Task> {
        val list = mutableListOf<Task>()

        val db = this.readableDatabase
        val query = "SELECT * FROM " + TABLE_NAME + ";"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val task = Task()
                task.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                task.title = result.getString(result.getColumnIndex(COL_TITLE))
                task.date = result.getString(result.getColumnIndex(COL_DATE))
                task.time = result.getString(result.getColumnIndex(COL_TIME))
                task.describe = result.getString(result.getColumnIndex(COL_DESCRIBE))
                list.add(task)
            } while (result.moveToNext())
        }

        result.close()
        return list
    }

    fun deleteTask(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, COL_ID+"=?", arrayOf(id.toString()))
        db.close()
    }

    @SuppressLint("Range")
    fun findById(id: Int) : Task {
        val task = Task()
        val db = this.readableDatabase
        val query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + "="+ id +";"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            task.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
            task.title = result.getString(result.getColumnIndex(COL_TITLE))
            task.date = result.getString(result.getColumnIndex(COL_DATE))
            task.time = result.getString(result.getColumnIndex(COL_TIME))
            task.describe = result.getString(result.getColumnIndex(COL_DESCRIBE))
        }
        db.close()
        return task
    }

    fun atualizar(task: Task) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_TITLE, task.title)
        cv.put(COL_DATE, task.date)
        cv.put(COL_TIME, task.time)
        cv.put(COL_DESCRIBE, task.describe)
        db.update(TABLE_NAME, cv, COL_ID + "=?", arrayOf(task.id.toString()))
        db.close()
    }

}