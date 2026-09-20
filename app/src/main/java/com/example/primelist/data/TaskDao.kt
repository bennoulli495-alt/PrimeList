package com.example.primelist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE isTemplate = 0 ORDER BY createdAt DESC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE isTemplate = 0 AND categoryName = :categoryName ORDER BY createdAt DESC")
    fun getTasksByCategory(categoryName: String): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE isTemplate = 1 ORDER BY createdAt DESC")
    fun getAllTemplates(): Flow<List<Task>>

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}
