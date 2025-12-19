package com.vibecoding.baitap11.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.vibecoding.baitap11.model.Task
import com.vibecoding.baitap11.model.User

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "TodoList.db"
        private const val DATABASE_VERSION = 1

        // User table
        const val TABLE_USERS = "users"
        const val COLUMN_USER_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"

        // Task table
        const val TABLE_TASKS = "tasks"
        const val COLUMN_TASK_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_IS_COMPLETED = "is_completed"
        const val COLUMN_TASK_USER_ID = "user_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUserTable = ("CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")")
        db.execSQL(createUserTable)

        val createTaskTable = ("CREATE TABLE " + TABLE_TASKS + "("
                + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_IS_COMPLETED + " INTEGER,"
                + COLUMN_TASK_USER_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_TASK_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")" + ")")
        db.execSQL(createTaskTable)
        
        // Add admin user
        val values = ContentValues()
        values.put(COLUMN_USERNAME, "admin")
        values.put(COLUMN_PASSWORD, "123")
        db.insert(TABLE_USERS, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    }

    // User Operations
    fun addUser(user: User): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USERNAME, user.username)
        values.put(COLUMN_PASSWORD, user.password)
        val id = db.insert(TABLE_USERS, null, values)
        db.close()
        return id
    }

    fun checkUser(username: String): Boolean {
        val db = this.readableDatabase
        val columns = arrayOf(COLUMN_USER_ID)
        val selection = "$COLUMN_USERNAME = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null)
        val count = cursor.count
        cursor.close()
        db.close()
        return count > 0
    }

    fun checkUser(username: String, password: String): User? {
        val db = this.readableDatabase
        val columns = arrayOf(COLUMN_USER_ID, COLUMN_USERNAME, COLUMN_PASSWORD)
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null)
        
        var user: User? = null
        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(COLUMN_USER_ID)
            val usernameIndex = cursor.getColumnIndex(COLUMN_USERNAME)
            val passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD)
            
            if (idIndex != -1 && usernameIndex != -1 && passwordIndex != -1) {
                user = User(
                    id = cursor.getInt(idIndex),
                    username = cursor.getString(usernameIndex),
                    password = cursor.getString(passwordIndex)
                )
            }
        }
        cursor.close()
        db.close()
        return user
    }

    // Task Operations
    fun addTask(task: Task): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, task.title)
        values.put(COLUMN_DESCRIPTION, task.description)
        values.put(COLUMN_IS_COMPLETED, if (task.isCompleted) 1 else 0)
        values.put(COLUMN_TASK_USER_ID, task.userId)
        val id = db.insert(TABLE_TASKS, null, values)
        db.close()
        return id
    }

    fun getAllTasks(userId: Int): List<Task> {
        val taskList = ArrayList<Task>()
        val db = this.readableDatabase
        val selection = "$COLUMN_TASK_USER_ID = ?"
        val selectionArgs = arrayOf(userId.toString())
        
        // Sắp xếp: chưa hoàn thành (is_completed = 0) trước, hoàn thành (is_completed = 1) sau
        val orderBy = "$COLUMN_IS_COMPLETED ASC, $COLUMN_TASK_ID DESC"
        
        val cursor = db.query(TABLE_TASKS, null, selection, selectionArgs, null, null, orderBy)

        if (cursor.moveToFirst()) {
            do {
                val idIndex = cursor.getColumnIndex(COLUMN_TASK_ID)
                val titleIndex = cursor.getColumnIndex(COLUMN_TITLE)
                val descIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION)
                val completeIndex = cursor.getColumnIndex(COLUMN_IS_COMPLETED)
                val userIdIndex = cursor.getColumnIndex(COLUMN_TASK_USER_ID)
                
                if (idIndex != -1) {
                    val task = Task(
                        id = cursor.getInt(idIndex),
                        title = cursor.getString(titleIndex),
                        description = cursor.getString(descIndex),
                        isCompleted = cursor.getInt(completeIndex) == 1,
                        userId = cursor.getInt(userIdIndex)
                    )
                    taskList.add(task)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return taskList
    }

    fun updateTask(task: Task): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, task.title)
        values.put(COLUMN_DESCRIPTION, task.description)
        values.put(COLUMN_IS_COMPLETED, if (task.isCompleted) 1 else 0)

        val result = db.update(TABLE_TASKS, values, "$COLUMN_TASK_ID = ?", arrayOf(task.id.toString()))
        db.close()
        return result
    }

    fun deleteTask(taskId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_TASKS, "$COLUMN_TASK_ID = ?", arrayOf(taskId.toString()))
        db.close()
    }
}