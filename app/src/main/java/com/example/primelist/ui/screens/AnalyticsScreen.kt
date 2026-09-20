package com.example.primelist.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.primelist.ui.theme.*

data class DayProgress(val label: String, val value: Float)
data class CategoryBreakdown(val title: String, val taskCount: Int, val progress: Float)

@Composable
fun AnalyticsScreen(onBackClick: () -> Unit) {
    val completionRate = remember { 0.72f }
    val streakDays = remember { 6 }
    val completedToday = remember { 5 }
    val remainingToday = remember { 3 }
    val totalAllTime = remember { 248 }
    val bestDay = remember { "Wednesday" }
    val worstDay = remember { "Sunday" }

    val weeklyProgress = remember {
        listOf(
            DayProgress("Mon", 0.5f),
            DayProgress("Tue", 0.7f),
            DayProgress("Wed", 0.9f),
            DayProgress("Thu", 0.4f),
            DayProgress("Fri", 0.6f),
            DayProgress("Sat", 0.3f),
            DayProgress("Sun", 0.2f)
        )
    }

    val categoryBreakdown = remember {
        listOf(
            CategoryBreakdown("Business", 10, 0.6f),
            CategoryBreakdown("Personal", 10, 0.4f)
        )
    }

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
                .verticalScroll(rememberScrollState())
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
                    text = "Analytics",
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CompletionRingCard(
                    completionRate = completionRate,
                    modifier = Modifier.weight(1f)
                )
                StreakCard(
                    streakDays = streakDays,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "THIS WEEK",
                color = TextMuted,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(10.dp))
            WeeklyGraphCard(weeklyProgress = weeklyProgress)

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "TODAY",
                color = TextMuted,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    label = "Completed",
                    value = "$completedToday",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Remaining",
                    value = "$remainingToday",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "CATEGORIES",
                color = TextMuted,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(10.dp))
            CategoryBreakdownCard(categories = categoryBreakdown)

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    label = "Best day",
                    value = bestDay,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Worst day",
                    value = worstDay,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            TotalTasksCard(total = totalAllTime)

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun CompletionRingCard(completionRate: Float, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(DarkNavyCard)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(74.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(74.dp)) {
                drawArc(
                    color = DarkNavyCardLight,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 8f, cap = StrokeCap.Round)
                )
                drawArc(
                    brush = Brush.linearGradient(
                        listOf(MagentaGradientStart, MagentaGradientEnd)
                    ),
                    startAngle = -90f,
                    sweepAngle = 360f * completionRate,
                    useCenter = false,
                    style = Stroke(width = 8f, cap = StrokeCap.Round)
                )
            }
            Text(
                text = "${(completionRate * 100).toInt()}%",
                color = TextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Completion rate",
            color = TextMuted,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun StreakCard(streakDays: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(DarkNavyCard)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(74.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.LocalFireDepartment,
                contentDescription = "Streak",
                tint = PinkAccentLight,
                modifier = Modifier.size(36.dp)
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "$streakDays days",
            color = TextPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Current streak",
            color = TextMuted,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun WeeklyGraphCard(weeklyProgress: List<DayProgress>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DarkNavyCard)
            .padding(16.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            val stepX = size.width / (weeklyProgress.size - 1)
            val points = weeklyProgress.mapIndexed { index, day ->
                Offset(stepX * index, size.height * (1f - day.value))
            }
            for (i in 0 until points.size - 1) {
                drawLine(
                    brush = Brush.linearGradient(listOf(PinkAccent, PinkAccentLight)),
                    start = points[i],
                    end = points[i + 1],
                    strokeWidth = 4f,
                    cap = StrokeCap.Round
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            weeklyProgress.forEach { day ->
                Text(
                    text = day.label,
                    color = TextMuted,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(DarkNavyCard)
            .padding(16.dp)
    ) {
        Text(
            text = value,
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = TextMuted,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun CategoryBreakdownCard(categories: List<CategoryBreakdown>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DarkNavyCard)
            .padding(16.dp)
    ) {
        categories.forEachIndexed { index, category ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = category.title,
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${category.taskCount} tasks",
                    color = TextMuted,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
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
            if (index != categories.lastIndex) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun TotalTasksCard(total: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DarkNavyCard)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Total tasks",
                color = TextMuted,
                fontSize = 12.sp
            )
            Text(
                text = "All time",
                color = TextMuted,
                fontSize = 11.sp
            )
        }
        Text(
            text = "$total",
            color = TextPrimary,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
