package com.vibecoding.baitap10

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoListActivity : AppCompatActivity() {

    private lateinit var dbHelper: TodoDatabaseHelper
    private lateinit var adapter: TodoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_todolist)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = TodoDatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerView)
        fabAdd = findViewById(R.id.fabAdd)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TodoAdapter(dbHelper.getAllTodos(), ::showEditDialog, ::deleteTodo)
        recyclerView.adapter = adapter

        fabAdd.setOnClickListener {
            showAddDialog()
        }
    }

    private fun loadTodos() {
        val todos = dbHelper.getAllTodos()
        adapter.updateData(todos)
    }

    private fun showAddDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_todo, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.etTitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)

        AlertDialog.Builder(this)
            .setTitle("Thêm công việc")
            .setView(dialogView)
            .setPositiveButton("Thêm") { _, _ ->
                val title = etTitle.text.toString()
                val description = etDescription.text.toString()
                if (title.isNotEmpty()) {
                    val todo = Todo(title = title, description = description)
                    dbHelper.insertTodo(todo)
                    loadTodos()
                } else {
                    Toast.makeText(this, "Tiêu đề không được để trống", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun showEditDialog(todo: Todo) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_todo, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.etTitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)

        etTitle.setText(todo.title)
        etDescription.setText(todo.description)

        AlertDialog.Builder(this)
            .setTitle("Sửa công việc")
            .setView(dialogView)
            .setPositiveButton("Lưu") { _, _ ->
                val title = etTitle.text.toString()
                val description = etDescription.text.toString()
                if (title.isNotEmpty()) {
                    val updatedTodo = todo.copy(title = title, description = description)
                    dbHelper.updateTodo(updatedTodo)
                    loadTodos()
                } else {
                    Toast.makeText(this, "Tiêu đề không được để trống", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun deleteTodo(todo: Todo) {
        AlertDialog.Builder(this)
            .setTitle("Xóa công việc")
            .setMessage("Bạn có chắc chắn muốn xóa công việc này?")
            .setPositiveButton("Xóa") { _, _ ->
                dbHelper.deleteTodo(todo.id)
                loadTodos()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
}
