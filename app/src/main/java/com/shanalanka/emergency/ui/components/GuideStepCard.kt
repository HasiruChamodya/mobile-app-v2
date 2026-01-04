package com.shanalanka.emergency.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shanalanka.emergency.data.model.GuideStep
import com.shanalanka.emergency.ui.theme.SahanaLankaTheme

/**
 * Card component displaying a single step in an emergency guide.
 * 
 * @param step The guide step to display
 * @param modifier Modifier for the card
 */
@Composable
fun GuideStepCard(
    step: GuideStep,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Step number badge
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            ) {
                Box(
                    contentAlignment = androidx.compose.ui.Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = step.stepNumber.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Step content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = step.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = step.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuideStepCardPreview() {
    SahanaLankaTheme {
        GuideStepCard(
            step = GuideStep(
                stepNumber = 1,
                title = "Check Responsiveness",
                description = "Tap the person's shoulder and shout. Check if they respond. Look for normal breathing."
            )
        )
    }
}

@Preview(showBackground = true, name = "Long Description")
@Composable
fun GuideStepCardLongPreview() {
    SahanaLankaTheme {
        GuideStepCard(
            step = GuideStep(
                stepNumber = 5,
                title = "Perform Compressions",
                description = "Push hard and fast - 30 compressions at 100-120 per minute. Press down at least 2 inches deep. Allow chest to fully recoil between compressions."
            )
        )
    }
}
