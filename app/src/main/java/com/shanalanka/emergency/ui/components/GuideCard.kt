package com.shanalanka.emergency.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shanalanka.emergency.data.model.EmergencyGuide
import com.shanalanka.emergency.data.model.GuideCategory
import com.shanalanka.emergency.ui.theme.SahanaLankaTheme

/**
 * Card component displaying emergency guide information.
 * 
 * @param guide Emergency guide to display
 * @param onGuideClick Callback when guide card is clicked
 * @param onBookmarkClick Callback when bookmark button is clicked
 * @param modifier Modifier for the card
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuideCard(
    guide: EmergencyGuide,
    onGuideClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onGuideClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Title
                Text(
                    text = guide.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Category badge
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = getCategoryColor(guide.category),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = guide.category.displayName,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Description
                Text(
                    text = guide.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Step count
                Text(
                    text = "${guide.steps.size} steps",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // Bookmark button
            IconButton(
                onClick = onBookmarkClick
            ) {
                Icon(
                    imageVector = if (guide.isBookmarked) {
                        Icons.Filled.Bookmark
                    } else {
                        Icons.Outlined.BookmarkBorder
                    },
                    contentDescription = if (guide.isBookmarked) {
                        "Remove bookmark"
                    } else {
                        "Add bookmark"
                    },
                    tint = if (guide.isBookmarked) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}

/**
 * Get color for guide category.
 */
@Composable
private fun getCategoryColor(category: GuideCategory): androidx.compose.ui.graphics.Color {
    return when (category) {
        GuideCategory.BREATHING -> androidx.compose.ui.graphics.Color(0xFF2196F3) // Blue
        GuideCategory.INJURIES -> androidx.compose.ui.graphics.Color(0xFFD32F2F) // Red
        GuideCategory.MEDICAL -> androidx.compose.ui.graphics.Color(0xFF7B1FA2) // Purple
        GuideCategory.ENVIRONMENTAL -> androidx.compose.ui.graphics.Color(0xFF388E3C) // Green
        GuideCategory.OTHER -> androidx.compose.ui.graphics.Color(0xFF616161) // Gray
    }
}

@Preview(showBackground = true)
@Composable
fun GuideCardPreview() {
    SahanaLankaTheme {
        GuideCard(
            guide = EmergencyGuide(
                id = "cpr_adult",
                title = "CPR - Adult",
                category = GuideCategory.BREATHING,
                description = "Cardiopulmonary resuscitation for adults (over 8 years)",
                steps = List(8) { 
                    com.shanalanka.emergency.data.model.GuideStep(
                        it + 1, 
                        "Step ${it + 1}", 
                        "Description"
                    ) 
                },
                warnings = emptyList(),
                whenToCallEmergency = "Call immediately",
                iconRes = android.R.drawable.ic_menu_help,
                isBookmarked = false
            ),
            onGuideClick = {},
            onBookmarkClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Bookmarked")
@Composable
fun GuideCardBookmarkedPreview() {
    SahanaLankaTheme {
        GuideCard(
            guide = EmergencyGuide(
                id = "snake_bite",
                title = "Snake Bite First Aid",
                category = GuideCategory.ENVIRONMENTAL,
                description = "First aid treatment for venomous snake bites",
                steps = List(9) { 
                    com.shanalanka.emergency.data.model.GuideStep(
                        it + 1, 
                        "Step ${it + 1}", 
                        "Description"
                    ) 
                },
                warnings = emptyList(),
                whenToCallEmergency = "Call immediately",
                iconRes = android.R.drawable.ic_menu_help,
                isBookmarked = true
            ),
            onGuideClick = {},
            onBookmarkClick = {}
        )
    }
}
