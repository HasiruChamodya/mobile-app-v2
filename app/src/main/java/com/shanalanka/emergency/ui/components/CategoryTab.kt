package com.shanalanka.emergency.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shanalanka.emergency.data.model.GuideCategory
import com.shanalanka.emergency.ui.theme.SahanaLankaTheme

/**
 * Horizontal scrollable row of category filter tabs.
 * 
 * @param selectedCategory Currently selected category (null for "All")
 * @param onCategorySelected Callback when a category is selected
 * @param modifier Modifier for the row
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryTab(
    selectedCategory: GuideCategory?,
    onCategorySelected: (GuideCategory?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // "All Categories" chip
        FilterChip(
            selected = selectedCategory == null,
            onClick = { onCategorySelected(null) },
            label = { Text("All") }
        )
        
        // Individual category chips
        GuideCategory.entries.forEach { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                label = { Text(category.displayName) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryTabPreview() {
    SahanaLankaTheme {
        CategoryTab(
            selectedCategory = null,
            onCategorySelected = {}
        )
    }
}

@Preview(showBackground = true, name = "Selected Category")
@Composable
fun CategoryTabSelectedPreview() {
    SahanaLankaTheme {
        CategoryTab(
            selectedCategory = GuideCategory.BREATHING,
            onCategorySelected = {}
        )
    }
}
