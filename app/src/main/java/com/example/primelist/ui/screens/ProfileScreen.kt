package com.example.primelist.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.primelist.ui.theme.*

@Composable
fun ProfileScreen() {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavyBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = TextPrimary
            )

            Spacer(modifier = Modifier.height(28.dp))

            AvatarWithRing()

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Olivia",
                color = TextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Mitchell",
                color = TextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(28.dp))
            MenuRow(icon = Icons.Filled.Description, label = "Templates")
            Spacer(modifier = Modifier.height(18.dp))
            MenuRow(icon = Icons.Filled.Category, label = "Categories")
            Spacer(modifier = Modifier.height(18.dp))
            MenuRow(icon = Icons.Filled.BarChart, label = "Analytics")

            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "Consistancy",
                color = TextMuted,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(12.dp))
            ConsistencyGraph()
        }
    }
}

@Composable
private fun AvatarWithRing() {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier.size(90.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(90.dp)) {
            drawCircle(
                brush = Brush.linearGradient(
                    listOf(MagentaGradientStart, MagentaGradientEnd)
                ),
                radius = size.minDimension / 2,
                style = Stroke(width = 6f, cap = StrokeCap.Round)
            )
        }
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(74.dp)
                .clip(CircleShape)
                .background(DarkNavyCard)
        )
        // Placeholder avatar — replace with Image(painter = ..., ...) once a real photo is available
        Text(
            text = "OM",
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun MenuRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = TextSecondary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = label,
            color = TextPrimary,
            fontSize = 15.sp
        )
    }
}

@Composable
private fun ConsistencyGraph() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        val width = size.width
        val height = size.height
        val points = listOf(
            Offset(0f, height * 0.6f),
            Offset(width * 0.15f, height * 0.3f),
            Offset(width * 0.3f, height * 0.5f),
            Offset(width * 0.45f, height * 0.1f),
            Offset(width * 0.6f, height * 0.7f),
            Offset(width * 0.75f, height * 0.2f),
            Offset(width * 0.9f, height * 0.5f),
            Offset(width, height * 0.35f)
        )

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
}
