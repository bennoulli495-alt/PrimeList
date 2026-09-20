package com.example.primelist.data

import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao
) {

    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    val allTemplates: Flow<List<Task>> = taskDao.getAllTemplates()
    val allCategories: Flow<List<Category>> = categoryDao.getAllCategories()

    fun getTasksByCategory(categoryName: String): Flow<List<Task>> =
        taskDao.getTasksByCategory(categoryName)

    suspend fun addTask(task: Task) = taskDao.insertTask(task)

    suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    suspend fun toggleTask(task: Task) =
        taskDao.updateTask(task.copy(isChecked = !task.isChecked))

    suspend fun addCategory(name: String) =
        categoryDao.insertCategory(Category(name = name))

    suspend fun deleteCategory(category: Category) =
        categoryDao.deleteCategory(category)

    suspend fun seedDefaultCategoriesIfEmpty() {
        categoryDao.insertCategory(Category(name = "Business"))
        categoryDao.insertCategory(Category(name = "Personal"))
    }
}
