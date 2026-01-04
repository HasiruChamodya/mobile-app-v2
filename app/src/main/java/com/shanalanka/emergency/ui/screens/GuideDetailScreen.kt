package com.shanalanka.emergency.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shanalanka.emergency.data.model.EmergencyGuide
import com.shanalanka.emergency.data.model.GuideCategory
import com.shanalanka.emergency.data.model.GuideStep
import com.shanalanka.emergency.ui.components.GuideStepCard
import com.shanalanka.emergency.ui.theme.SahanaLankaTheme
import com.shanalanka.emergency.ui.viewmodel.EmergencyGuidesViewModel
import kotlinx.coroutines.launch

/**
 * Guide Detail screen showing full emergency guide with steps, warnings, and emergency info.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuideDetailScreen(
    guideId: String,
    viewModel: EmergencyGuidesViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var guide by remember { mutableStateOf<EmergencyGuide?>(null) }
    
    LaunchedEffect(guideId) {
        scope.launch {
            guide = viewModel.getGuideById(guideId)
        }
    }
    
    if (guide == null) {
        // Loading state
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        GuideDetailContent(
            guide = guide!!,
            onNavigateBack = onNavigateBack,
            onBookmarkClick = {
                viewModel.toggleBookmark(guide!!.id, guide!!.isBookmarked)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GuideDetailContent(
    guide: EmergencyGuide,
    onNavigateBack: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(guide.title) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onBookmarkClick) {
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
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Description section
            item {
                Text(
                    text = guide.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            // When to call emergency section
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "When to Call Emergency (119)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = guide.whenToCallEmergency,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
            }
            
            // Steps section
            item {
                Text(
                    text = "Steps to Follow",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            items(guide.steps) { step ->
                GuideStepCard(step = step)
            }
            
            // Warnings section
            if (guide.warnings.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Important Warnings",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                
                items(guide.warnings) { warning ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = warning,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
            
            // Disclaimer at bottom
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(
                        text = "⚠️ This guide provides general first aid information. " +
                                "Always seek professional medical help in emergencies. " +
                                "Call 119 or visit the nearest hospital immediately if needed.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuideDetailScreenPreview() {
    SahanaLankaTheme {
        GuideDetailContent(
            guide = EmergencyGuide(
                id = "cpr_adult",
                title = "CPR - Adult",
                category = GuideCategory.BREATHING,
                description = "Cardiopulmonary resuscitation for adults (over 8 years)",
                steps = listOf(
                    GuideStep(1, "Check Responsiveness", "Tap the person's shoulder and shout."),
                    GuideStep(2, "Call Emergency", "Call 119 immediately."),
                    GuideStep(3, "Position Person", "Lay person flat on firm surface.")
                ),
                warnings = listOf(
                    "Do not stop CPR until help arrives",
                    "If not trained, do hands-only CPR"
                ),
                whenToCallEmergency = "Call 119 immediately if person is unresponsive.",
                iconRes = android.R.drawable.ic_menu_help,
                isBookmarked = false
            ),
            onNavigateBack = {},
            onBookmarkClick = {}
        )
    }
}
