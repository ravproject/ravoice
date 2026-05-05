package com.ravproject.ravoice.core.designsystem

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OverlayContent(
    onTranslateToggle: (Boolean) -> Unit,
    onVoiceChange: (Float) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var currentMode by remember { mutableStateOf("Translate") }
    
    val glassColor = Color.White.copy(alpha = 0.15f)
    val accentColor = Color(0xFF6200EE)

    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(glassColor, glassColor.copy(alpha = 0.05f))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main Toggle Button
            IconButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier
                    .size(48.dp)
                    .background(accentColor, CircleShape)
            ) {
                Icon(
                    imageVector = if (currentMode == "Translate") Icons.Default.Translate else Icons.Default.Mic,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(12.dp))
                
                // Live Transcript Area (Subtitle)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 60.dp)
                        .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Lawan Bicara: Hello, how are you?\nTerjemahan: Halo, apa kabar?",
                        color = Color.White,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                
                // Mode Switcher
                Row(modifier = Modifier.padding(4.dp)) {
                    Button(
                        onClick = { currentMode = "Translate" },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentMode == "Translate") accentColor else Color.DarkGray
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f).padding(2.dp)
                    ) {
                        Text("Translate", fontSize = 10.sp)
                    }
                    Button(
                        onClick = { currentMode = "Voice" },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentMode == "Voice") accentColor else Color.DarkGray
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f).padding(2.dp)
                    ) {
                        Text("Voice", fontSize = 10.sp)
                    }
                }

                if (currentMode == "Voice") {
                    VoiceControls(onVoiceChange)
                }
            }
        }
    }
}

@Composable
fun VoiceControls(onVoiceChange: (Float) -> Unit) {
    var pitch by remember { mutableStateOf(1.0f) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Pitch: ${String.format("%.1f", pitch)}x", color = Color.White, fontSize = 11.sp)
        Slider(
            value = pitch,
            onValueChange = { 
                pitch = it
                onVoiceChange(it)
            },
            valueRange = 0.5f..2.0f,
            modifier = Modifier.width(100.dp)
        )
    }
}
