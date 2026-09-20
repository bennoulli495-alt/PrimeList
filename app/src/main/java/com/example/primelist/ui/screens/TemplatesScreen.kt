package com.example.primelist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.primelist.data.Task
import com.example.primelist.ui.theme.*
import com.example.primelist.viewmodel.TaskViewModel

@Composable
fun TemplatesScreen(
    onBackClick: () -> Unit,
    viewModel: TaskViewModel = viewModel()
) {
    val templates by viewModel.allTemplates.collectAsState()
    val categories by viewModel.allCategories.collectAsState()

    var showAddTemplateSheet by remember { mutableStateOf(false) }

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
                    text = "Templates",
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "${templates.size} templates",
                color = TextMuted,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (templates.isEmpty()) {
                EmptyTemplatesState()
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(templates) { template ->
                        TemplateCard(
                            template = template,
                            onUseClick = {
                                viewModel.addTask(template.title, template.categoryName, null)
                            }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }

        FloatingActionButton(
            onClick = { showAddTemplateSheet = true },
            containerColor = PinkAccent,
            contentColor = TextPrimary,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add template")
        }

        if (showAddTemplateSheet) {
            AddTemplateBottomSheet(
                categoryNames = categories.map { it.name },
                onDismiss = { showAddTemplateSheet = false },
                onSaveTemplate = { title, category ->
                    viewModel.addTask(title, category, null, isTemplate = true)
                    showAddTemplateSheet = false
                }
            )
        }
    }
}

@Composable
private fun TemplateCard(template: Task, onUseClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(DarkNavyCard)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Description,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = template.title,
                color = TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(DarkNavyCardLight)
                    .padding(horizontal = 10.dp, vertical = 3.dp)
            ) {
                Text(
                    text = template.categoryName,
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Filled.PlaylistAdd,
            contentDescription = "Use template",
            tint = PinkAccentLight,
            modifier = Modifier
                .size(24.dp)
                .clickable { onUseClick() }
        )
    }
}

@Composable
private fun EmptyTemplatesState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Description,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "No templates yet",
            color = TextMuted,
            fontSize = 14.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTemplateBottomSheet(
    categoryNames: List<String>,
    onDismiss: () -> Unit,
    onSaveTemplate: (title: String, category: String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var templateTitle by remember { mutableStateOf("") }
    var selectedCategory by remember(categoryNames) {
        mutableStateOf(categoryNames.firstOrNull() ?: "")
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = DarkNavyCard
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = "New Template",
                color = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = templateTitle,
                onValueChange = { templateTitle = it },
                placeholder = { Text("Template title", color = TextMuted) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedBorderColor = PinkAccent,
                    unfocusedBorderColor = DividerColor,
                    cursorColor = PinkAccent
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "CATEGORY",
                color = TextMuted,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(10.dp))

            if (categoryNames.isEmpty()) {
                Text(
                    text = "No categories yet — add one from Categories first",
                    color = TextMuted,
                    fontSize = 12.sp
                )
            } else {
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    categoryNames.forEach { name ->
                        TemplateCategoryChip(
                            label = name,
                            isSelected = selectedCategory == name,
                            onClick = { selectedCategory = name }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    if (templateTitle.isNotBlank() && selectedCategory.isNotBlank()) {
                        onSaveTemplate(templateTitle, selectedCategory)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PinkAccent,
                    contentColor = TextPrimary
                )
            ) {
                Text(text = "Save Template", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun TemplateCategoryChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) PinkAccent else DarkNavyCardLight)
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 10.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) TextPrimary else TextSecondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
