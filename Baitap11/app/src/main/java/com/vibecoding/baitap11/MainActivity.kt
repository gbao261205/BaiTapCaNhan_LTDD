package com.vibecoding.baitap11

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vibecoding.baitap11.databinding.ActivityMainBinding
import com.vibecoding.baitap11.databinding.DialogEditTaskBinding
import com.vibecoding.baitap11.model.Task
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
        // Khởi tạo adapter
        adapter = TaskAdapter(
            onTaskChecked = { task, isChecked ->
                viewModel.onTaskChecked(task, isChecked)
            },
            onDeleteClicked = { task ->
                viewModel.onDeleteTask(task)
            },
            onItemClicked = { task ->
                showEditTaskDialog(task)
            }
        )
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = adapter
        
        // Đảm bảo ItemAnimator được bật
        val itemAnimator = binding.rvTasks.itemAnimator
        if (itemAnimator is DefaultItemAnimator) {
            itemAnimator.supportsChangeAnimations = true
        }
    }

    private fun setupObservers() {
        viewModel.tasks.observe(this) { tasks ->
            adapter.submitList(ArrayList(tasks))
        }

        viewModel.message.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showEditTaskDialog(task: Task) {
        val dialogBinding = DialogEditTaskBinding.inflate(LayoutInflater.from(this))
        dialogBinding.task = task
        
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnSave.setOnClickListener {
            val newTitle = dialogBinding.etEditTitle.text.toString()
            val newDesc = dialogBinding.etEditDesc.text.toString()

            if (newTitle.isNotEmpty()) {
                viewModel.onUpdateTask(task, newTitle, newDesc)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}