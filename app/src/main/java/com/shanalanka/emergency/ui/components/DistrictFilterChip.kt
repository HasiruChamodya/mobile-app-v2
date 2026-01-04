package com.shanalanka.emergency.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shanalanka.emergency.data.model.District
import com.shanalanka.emergency.ui.theme.SahanaLankaTheme

/**
 * Horizontal scrollable row of district filter chips.
 * 
 * @param selectedDistrict Currently selected district (null for "All")
 * @param onDistrictSelected Callback when a district is selected
 * @param modifier Modifier for the row
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistrictFilterChip(
    selectedDistrict: District?,
    onDistrictSelected: (District?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // "All Districts" chip
        FilterChip(
            selected = selectedDistrict == null,
            onClick = { onDistrictSelected(null) },
            label = { Text("All Districts") }
        )
        
        // Individual district chips
        District.entries.forEach { district ->
            FilterChip(
                selected = selectedDistrict == district,
                onClick = { onDistrictSelected(district) },
                label = { Text(district.displayName) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DistrictFilterChipPreview() {
    SahanaLankaTheme {
        DistrictFilterChip(
            selectedDistrict = null,
            onDistrictSelected = {}
        )
    }
}

@Preview(showBackground = true, name = "Selected District")
@Composable
fun DistrictFilterChipSelectedPreview() {
    SahanaLankaTheme {
        DistrictFilterChip(
            selectedDistrict = District.COLOMBO,
            onDistrictSelected = {}
        )
    }
}
