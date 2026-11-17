package com.mwema.afyapoa.ui.settings.profileInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmergencyContactsSection(
    contacts: List<EmergencyContact>,
    onAddContact: () -> Unit,
    onRemoveContact: (Int) -> Unit,
    onUpdateContact: (Int, EmergencyContact) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            10.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Emergency Contacts",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            contacts.forEachIndexed { index, contact ->

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {

                    OutlinedTextField(
                        value = contact.name,
                        onValueChange = {
                            onUpdateContact(
                                index,
                                contact.copy(name = it)
                            )
                        },
                        label = { Text("Full Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = contact.relationship,
                        onValueChange = {
                            onUpdateContact(
                                index,
                                contact.copy(relationship = it)
                            )
                        },
                        label = { Text("Relationship") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = contact.phoneNumber,
                        onValueChange = {
                            onUpdateContact(
                                index,
                                contact.copy(phoneNumber = it)
                            )
                        },
                        label = { Text("Phone Number") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = { onRemoveContact(index) }
                    ) {
                        Text(
                            text = "Remove Contact",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                Spacer(modifier = Modifier.height(12.dp))
            }

            TextButton(
                onClick = onAddContact
            ) {
                Text("+ Add Emergency Contact")
            }
        }
    }
}