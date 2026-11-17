package com.mwema.afyapoa.ui.settings.profileInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun DynamicListSection(
    title: String,
    items: List<String>,
    onAdd: () -> Unit,
    onRemove: (Int) -> Unit,
    onUpdate: (Int, String) -> Unit
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
        Column(Modifier.padding(16.dp)) {

            Text(title)

            Spacer(modifier = Modifier.height(8.dp))

            items.forEachIndexed { index, value ->
                Row(verticalAlignment = Alignment.CenterVertically) {

                    OutlinedTextField(
                        value = value,
                        onValueChange = { onUpdate(index, it) },
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onClick = { onRemove(index) }) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            TextButton(onClick = onAdd) {
                Text("+ Add")
            }
        }
    }
}