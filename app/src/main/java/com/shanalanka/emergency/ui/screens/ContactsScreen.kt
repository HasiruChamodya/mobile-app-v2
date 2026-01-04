package com.shanalanka.emergency.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shanalanka.emergency.data.models.EmergencyContact
import com.shanalanka.emergency.ui.components.ContactCard
import com.shanalanka.emergency.ui.theme.SahanaLankaTheme

/**
 * Screen for managing emergency contacts.
 *
 * @param contacts List of current emergency contacts
 * @param onAddContact Callback when add contact button is clicked
 * @param onDeleteContact Callback when a contact is deleted
 * @param onNavigateBack Callback to navigate back
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    contacts: List<EmergencyContact>,
    onAddContact: () -> Unit,
    onDeleteContact: (EmergencyContact) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Emergency Contacts",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            if (contacts.size < EmergencyContact.MAX_CONTACTS) {
                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add contact"
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (contacts.isEmpty()) {
                // Empty state
                EmptyContactsState(
                    onAddClick = { showAddDialog = true },
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Contact limit indicator
                    Surface(
                        color = if (contacts.size >= EmergencyContact.MAX_CONTACTS) {
                            MaterialTheme.colorScheme.errorContainer
                        } else {
                            MaterialTheme.colorScheme.primaryContainer
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${contacts.size}/${EmergencyContact.MAX_CONTACTS} contacts added",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    
                    // Contacts list
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(contacts, key = { it.id }) { contact ->
                            ContactCard(
                                contact = contact,
                                onDeleteClick = { onDeleteContact(contact) }
                            )
                        }
                    }
                }
            }
        }
    }
    
    // Add contact dialog
    if (showAddDialog) {
        AddContactDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, phone ->
                showAddDialog = false
                onAddContact()
            }
        )
    }
}

/**
 * Empty state when no contacts are added.
 */
@Composable
private fun EmptyContactsState(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.PersonAdd,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "No Emergency Contacts",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Add emergency contacts to send alerts in critical situations",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onAddClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Add First Contact")
        }
    }
}

/**
 * Dialog for adding a new contact.
 */
@Composable
private fun AddContactDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.PersonAdd,
                contentDescription = null
            )
        },
        title = {
            Text(
                text = "Add Emergency Contact",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("+94 77 123 4567") }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "You can also select from your phone contacts",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(name, phoneNumber) },
                enabled = name.isNotBlank() && phoneNumber.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ContactsScreenPreview() {
    SahanaLankaTheme {
        ContactsScreen(
            contacts = listOf(
                EmergencyContact(1, "John Doe", "+94 77 123 4567"),
                EmergencyContact(2, "Jane Smith", "+94 71 987 6543"),
                EmergencyContact(3, "Bob Johnson", "+94 76 555 1234")
            ),
            onAddContact = { },
            onDeleteContact = { },
            onNavigateBack = { }
        )
    }
}

@Preview(showBackground = true, name = "Empty State")
@Composable
fun ContactsScreenEmptyPreview() {
    SahanaLankaTheme {
        ContactsScreen(
            contacts = emptyList(),
            onAddContact = { },
            onDeleteContact = { },
            onNavigateBack = { }
        )
    }
}

@Preview(showBackground = true, name = "Dark Theme")
@Composable
fun ContactsScreenDarkPreview() {
    SahanaLankaTheme(darkTheme = true) {
        ContactsScreen(
            contacts = listOf(
                EmergencyContact(1, "Emergency Service", "119")
            ),
            onAddContact = { },
            onDeleteContact = { },
            onNavigateBack = { }
        )
    }
}
