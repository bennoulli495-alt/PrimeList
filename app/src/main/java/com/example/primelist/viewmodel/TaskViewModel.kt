package com.example.primelist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.primelist.data.AppDatabase
import com.example.primelist.data.Category
import com.example.primelist.data.Task
import com.example.primelist.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository

    val allTasks: StateFlow<List<Task>>
    val allTemplates: StateFlow<List<Task>>
    val allCategories: StateFlow<List<Category>>

    init {
        val db = AppDatabase.getDatabase(application)
        repository = TaskRepository(db.taskDao(), db.categoryDao())

        allTasks = repository.allTasks.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        allTemplates = repository.allTemplates.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        allCategories = repository.allCategories.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        viewModelScope.launch {
            repository.seedDefaultCategoriesIfEmpty()
        }
    }

    fun getTasksByCategory(categoryName: String) = repository.getTasksByCategory(categoryName)

    fun addTask(title: String, categoryName: String, dueDateTime: Long?, isTemplate: Boolean = false) {
        viewModelScope.launch {
            repository.addTask(
                Task(
                    title = title,
                    categoryName = categoryName,
                    dueDateTime = dueDateTime,
                    isTemplate = isTemplate
                )
            )
        }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch { repository.toggleTask(task) }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch { repository.deleteTask(task) }
    }

    fun addCategory(name: String) {
        viewModelScope.launch { repository.addCategory(name) }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch { repository.deleteCategory(category) }
    }
}
