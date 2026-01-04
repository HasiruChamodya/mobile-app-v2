package com.shanalanka.emergency.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shanalanka.emergency.data.models.EmergencySettings
import com.shanalanka.emergency.ui.theme.SahanaLankaTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Emergency button that requires press-and-hold to activate.
 * Provides visual feedback with progress indicator and color change.
 *
 * @param onEmergencyTriggered Callback when button is held for required duration
 * @param modifier Modifier for the button
 * @param enabled Whether the button is enabled
 */
@Composable
fun EmergencyButton(
    onEmergencyTriggered: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var isPressed by remember { mutableStateOf(false) }
    val progress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    
    val buttonColor = when {
        !enabled -> MaterialTheme.colorScheme.surfaceVariant
        isPressed -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.error
    }
    
    Box(
        modifier = modifier
            .size(200.dp)
            .clip(CircleShape)
            .background(buttonColor)
            .semantics {
                contentDescription = "Emergency button. Press and hold for 3 seconds to send alert"
            }
            .pointerInput(enabled) {
                if (enabled) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            scope.launch {
                                progress.animateTo(
                                    targetValue = 1f,
                                    animationSpec = tween(
                                        durationMillis = EmergencySettings.PRESS_AND_HOLD_DURATION_MS.toInt(),
                                        easing = LinearEasing
                                    )
                                )
                                if (isPressed) {
                                    onEmergencyTriggered()
                                }
                            }
                            
                            try {
                                awaitRelease()
                            } finally {
                                isPressed = false
                                scope.launch {
                                    progress.snapTo(0f)
                                }
                            }
                        }
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (isPressed) {
            CircularProgressIndicator(
                progress = progress.value,
                modifier = Modifier.size(180.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 8.dp,
                trackColor = Color.Transparent
            )
        }
        
        Text(
            text = if (isPressed) "HOLD" else "SOS",
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp
            ),
            color = MaterialTheme.colorScheme.onError,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmergencyButtonPreview() {
    SahanaLankaTheme {
        Box(
            modifier = Modifier.size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            EmergencyButton(
                onEmergencyTriggered = { }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmergencyButtonDisabledPreview() {
    SahanaLankaTheme {
        Box(
            modifier = Modifier.size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            EmergencyButton(
                onEmergencyTriggered = { },
                enabled = false
            )
        }
    }
}
