package com.ando.bletester.ui.scanner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ando.bletester.ui.scanner.data.ScannerItem

@Composable
fun ScannerListItem(scannerItem: ScannerItem, onClick : (ScannerItem) -> Unit) {
    Column(
        modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick(scannerItem) }
        .padding(16.dp)
    ) {

        Text(text = scannerItem.deviceName ?: "Unknown Device", style = MaterialTheme.typography.titleMedium)
        Text(text = "Address: ${scannerItem.address}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "RSSI: ${scannerItem.rssi} dBm", style = MaterialTheme.typography.bodySmall)
    }
}