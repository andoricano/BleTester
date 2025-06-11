package com.ando.bletester.ui.advertising

import android.bluetooth.le.AdvertiseSettings
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ando.bletester.bluetooth.ble.advertiser.legacy.Advertiser


@Composable
fun LegacyAdvertiserControl() {
    val vm: AdvertisingViewModel = hiltViewModel()
    var isAdvertising by remember { mutableStateOf(false) }

    var mode by remember { mutableStateOf(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY) }
    var txPower by remember { mutableStateOf(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM) }
    var connectable by remember { mutableStateOf(false) }


    var includeName by remember { mutableStateOf(true) }
    var includeTxPower by remember { mutableStateOf(false) }
    var manufacturerIdText by remember { mutableStateOf("") }
    var manufacturerDataText by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        vm.startAdvertising()
                        isAdvertising = true
                    },
                    enabled = !isAdvertising
                ) {
                    Text("Advertising")
                }

                Button(
                    onClick = {
                        vm.stopAdvertising()
                        isAdvertising = false
                    },
                    enabled = isAdvertising
                ) {
                    Text("Stop")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // === Settings Section ===
            Text("Advertising Data", style = MaterialTheme.typography.titleMedium)

            SettingDropdown("Advertise Mode", mode, listOf(
                AdvertiseSettings.ADVERTISE_MODE_LOW_POWER to "Low Power",
                AdvertiseSettings.ADVERTISE_MODE_BALANCED to "Balanced",
                AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY to "Low Latency"
            )) { mode = it }

            SettingDropdown("Tx Power", txPower, listOf(
                AdvertiseSettings.ADVERTISE_TX_POWER_ULTRA_LOW to "Ultra Low",
                AdvertiseSettings.ADVERTISE_TX_POWER_LOW to "Low",
                AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM to "Medium",
                AdvertiseSettings.ADVERTISE_TX_POWER_HIGH to "High"
            )) { txPower = it }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Connectable")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(checked = connectable, onCheckedChange = { connectable = it })
            }

            Button(onClick = {
                vm.configureLegacyAdvertisingSetting(mode, txPower, connectable)
            }) {
                Text("Save")
            }

            Divider()

            // === Data Section ===
            Text("Advertising Data", style = MaterialTheme.typography.titleMedium)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Include Name")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(checked = includeName, onCheckedChange = { includeName = it })
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Include Tx Power")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(checked = includeTxPower, onCheckedChange = { includeTxPower = it })
            }

            OutlinedTextField(
                value = manufacturerIdText,
                onValueChange = { manufacturerIdText = it },
                label = { Text("Manufacturer ID (hex)") },
                singleLine = true
            )

            OutlinedTextField(
                value = manufacturerDataText,
                onValueChange = { manufacturerDataText = it },
                label = { Text("Manufacturer Data (hex)") },
                singleLine = true
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                //TODO(save string)
            }

            Button(onClick = {
                val id = manufacturerIdText.toIntOrNull(10)
                    ?: manufacturerIdText.removePrefix("0x").toIntOrNull(16)
                val data = manufacturerDataText.hexToByteArrayOrNull()

                vm.configureLegacyAdvertisingData(
                    includeName = includeName,
                    includeTxPower = includeTxPower,
                    manufacturerId = id,
                    manufacturerData = data
                )
            }) {
                Text("Save")
            }
        }
    }
}

@Composable
fun <T> SettingDropdown(
    label: String,
    selected: T,
    options: List<Pair<T, String>>,
    onSelect: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(label)
        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(options.find { it.first == selected }?.second ?: "")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { (value, label) ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            onSelect(value)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

fun String.hexToByteArrayOrNull(): ByteArray? {
    return try {
        chunked(2).map { it.toInt(16).toByte() }.toByteArray()
    } catch (e: Exception) {
        null
    }
}