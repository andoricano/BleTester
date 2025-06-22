package com.ando.bletester.ui.advertising

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


@Composable
fun LegacyAdvertiserControlScreen() {
    val vm: AdvertisingViewModel = hiltViewModel()
    var isAdvertising by remember { mutableStateOf(false) }

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
                        vm.startLegacyAdvertising()
                        isAdvertising = true
                    },
                    enabled = !isAdvertising
                ) {
                    Text("Advertising")
                }

                Button(
                    onClick = {
                        vm.stopLegacyAdvertising()
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
            LegacyAdvertiserControl(modifier = Modifier.padding(top = 25.dp))
            Divider()

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