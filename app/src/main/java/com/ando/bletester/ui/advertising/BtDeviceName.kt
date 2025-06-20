package com.ando.bletester.ui.advertising

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ando.bletester.bluetooth.ble.advertiser.legacy.Advertiser
import com.ando.bletester.ui.scanner.ScannerViewModel

@Composable
fun BtDeviceName(modifier : Modifier) {
    val vm: AdvertisingViewModel = hiltViewModel()

    var btName by remember { mutableStateOf("") }
    var savedName by remember { mutableStateOf<String>(vm.getBtDeviceName()) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Bluetooth Device Name",
            style = MaterialTheme.typography.titleMedium
        )

        TextField(
            value = btName,
            onValueChange = { btName = it },
            label = { Text("Device Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                vm.setBtDeviceName(btName)
                savedName = btName
            },
            enabled = btName.isNotBlank(),
        ) {
            Text("Save")
        }

        savedName.let {
            Text(text = "Current Bluetooth Name: $it", style = MaterialTheme.typography.bodyMedium)
        }
    }
}