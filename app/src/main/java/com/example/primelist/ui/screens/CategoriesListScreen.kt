package com.example.primelist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.primelist.ui.theme.*
import com.example.primelist.viewmodel.TaskViewModel

@Composable
fun CategoriesListScreen(
    onBackClick: () -> Unit,
    viewModel: TaskViewModel = viewModel()
) {
    val categories by viewModel.allCategories.collectAsState()
    val tasks by viewModel.allTasks.collectAsState()

    val categoryStats = categories.map { category ->
        val categoryTasks = tasks.filter { it.categoryName == category.name }
        val completed = categoryTasks.count { it.isChecked }
        CategoryStat(
            title = category.name,
            taskCount = categoryTasks.size,
            progress = if (categoryTasks.isNotEmpty()) completed.toFloat() / categoryTasks.size else 0f
        )
    }

    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var showAddCategoryDialog by remember { mutableStateOf(false) }

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

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onBackClick() }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Categories",
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "${categoryStats.size} categories",
                color = TextMuted,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (categoryStats.isEmpty()) {
                Text(
                    text = "No categories yet — tap + to add one",
                    color = TextMuted,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 40.dp)
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    categoryStats.forEach { category ->
                        CategoryListCard(
                            category = category,
                            onClick = { selectedCategory = category.title }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }

        FloatingActionButton(
            onClick = { showAddCategoryDialog = true },
            containerColor = PinkAccent,
            contentColor = TextPrimary,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add category")
        }

        selectedCategory?.let { categoryName ->
            CategoryScreen(
                categoryName = categoryName,
                onBackClick = { selectedCategory = null }
            )
        }

        if (showAddCategoryDialog) {
            AddCategoryDialog(
                onDismiss = { showAddCategoryDialog = false },
                onConfirm = { name ->
                    viewModel.addCategory(name)
                    showAddCategoryDialog = false
                }
            )
        }
    }
}

@Composable
private fun CategoryListCard(category: CategoryStat, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DarkNavyCard)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(
            text = category.title,
            color = TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${category.taskCount} tasks",
            color = TextMuted,
            fontSize = 12.sp
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
private fun AddCategoryDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var name by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkNavyCard,
        title = {
            Text(text = "New Category", color = TextPrimary, fontWeight = FontWeight.Bold)
        },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Category name", color = TextMuted) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedBorderColor = PinkAccent,
                    unfocusedBorderColor = DividerColor,
                    cursorColor = PinkAccent
                )
            )
        },
        confirmButton = {
            TextButton(onClick = { if (name.isNotBlank()) onConfirm(name) }) {
                Text(text = "Add", color = PinkAccentLight, fontWeight = FontWeight.SemiBold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel", color = TextMuted)
            }
        }
    )
}
