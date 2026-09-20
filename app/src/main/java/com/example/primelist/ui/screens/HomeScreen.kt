package com.example.primelist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.primelist.data.Task
import com.example.primelist.ui.theme.*
import com.example.primelist.viewmodel.TaskViewModel

data class CategoryStat(
    val title: String,
    val taskCount: Int,
    val progress: Float
)

@Composable
fun HomeScreen(onMenuClick: () -> Unit, viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.allTasks.collectAsState()
    val categories by viewModel.allCategories.collectAsState()

    val categoryStats = categories.map { category ->
        val categoryTasks = tasks.filter { it.categoryName == category.name }
        val completed = categoryTasks.count { it.isChecked }
        CategoryStat(
            title = category.name,
            taskCount = categoryTasks.size,
            progress = if (categoryTasks.isNotEmpty()) completed.toFloat() / categoryTasks.size else 0f
        )
    }

    var showAddTaskSheet by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavyBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            TopBar(onMenuClick = onMenuClick)

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "What's up, Olivia!",
                color = TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "CATEGORIES",
                color = TextMuted,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))
            if (categoryStats.isEmpty()) {
                Text(
                    text = "No categories yet",
                    color = TextMuted,
                    fontSize = 13.sp
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    categoryStats.forEach { category ->
                        CategoryCard(
                            category = category,
                            modifier = Modifier.weight(1f),
                            onClick = { selectedCategory = category.title }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "TODAY'S TASKS",
                color = TextMuted,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(tasks) { task ->
                    TaskRow(
                        task = task,
                        onToggle = { viewModel.toggleTask(task) }
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }

        FloatingActionButton(
            onClick = { showAddTaskSheet = true },
            containerColor = PinkAccent,
            contentColor = TextPrimary,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add task")
        }

        if (showAddTaskSheet) {
            AddTaskBottomSheet(
                categoryNames = categories.map { it.name },
                onDismiss = { showAddTaskSheet = false },
                onAddTask = { title, category ->
                    viewModel.addTask(title, category, null)
                    showAddTaskSheet = false
                }
            )
        }

        selectedCategory?.let { categoryName ->
            CategoryScreen(
                categoryName = categoryName,
                onBackClick = { selectedCategory = null }
            )
        }
    }
}

@Composable
private fun TopBar(onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Menu",
            tint = TextPrimary,
            modifier = Modifier
                .size(24.dp)
                .clickable { onMenuClick() }
        )
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                tint = TextPrimary,
                modifier = Modifier.size(24.dp)
            )
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Notifications",
                tint = TextPrimary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun CategoryCard(category: CategoryStat, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(DarkNavyCard)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(
            text = "${category.taskCount} tasks",
            color = TextMuted,
            fontSize = 11.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = category.title,
            color = TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(DarkNavyCardLight)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(category.progress)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(MagentaGradientStart, MagentaGradientEnd)
                        )
                    )
            )
        }
    }
}

@Composable
private fun TaskRow(task: Task, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(DarkNavyCard)
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .clickable { onToggle() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (task.isChecked) Icons.Filled.CheckCircle else Icons.Filled.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (task.isChecked) CheckedGreen else TextMuted,
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = task.title,
            color = if (task.isChecked) TextMuted else TextPrimary,
            fontSize = 14.sp,
            textDecoration = if (task.isChecked) TextDecoration.LineThrough else TextDecoration.None
        )
    }
}
