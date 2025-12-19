package com.vibecoding.baitap11.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vibecoding.baitap11.databinding.ItemTaskBinding
import com.vibecoding.baitap11.model.Task

class TaskAdapter(
    private val onTaskChecked: (Task, Boolean) -> Unit,
    private val onDeleteClicked: (Task) -> Unit,
    private val onItemClicked: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.binding.task = task
        
        // Remove previous listener to avoid triggering it during recycling
        holder.binding.cbCompleted.setOnCheckedChangeListener(null)
        
        holder.binding.cbCompleted.isChecked = task.isCompleted

        holder.binding.cbCompleted.setOnCheckedChangeListener { _, isChecked ->
            // Chỉ gọi callback nếu trạng thái thực sự thay đổi so với dữ liệu hiện tại
            if (task.isCompleted != isChecked) {
                 onTaskChecked(task, isChecked)
            }
        }

        holder.binding.btnDelete.setOnClickListener {
            onDeleteClicked(task)
        }
        
        // Handle item click for editing
        holder.binding.root.setOnClickListener {
            onItemClicked(task)
        }
        
        holder.binding.executePendingBindings()
    }
    
    // DiffUtilCallback để so sánh sự khác biệt
    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            // So sánh dựa trên ID duy nhất
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            // So sánh toàn bộ nội dung (Kotlin data class tự có equals)
            return oldItem == newItem
        }
    }
}