package com.vibecoding.baitap11

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vibecoding.baitap11.databinding.ActivityMainBinding
import com.vibecoding.baitap11.view.TaskAdapter
import com.vibecoding.baitap11.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val userId = intent.getIntExtra("USER_ID", -1)
        if (userId == -1) {
            Toast.makeText(this, "Error: User not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        viewModel.init(userId)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(emptyList(),
            onTaskChecked = { task, isChecked ->
                viewModel.onTaskChecked(task, isChecked)
            },
            onDeleteClicked = { task ->
                viewModel.onDeleteTask(task)
            }
        )
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.tasks.observe(this) { tasks ->
            adapter.updateTasks(tasks)
        }

        viewModel.message.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}