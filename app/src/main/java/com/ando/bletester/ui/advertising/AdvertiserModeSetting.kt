package com.ando.bletester.ui.advertising

import android.bluetooth.le.AdvertiseSettings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import com.ando.bletester.NavRoutes
import com.ando.bletester.ui.components.buttons.BottomButton

@Composable
fun AdvertiserModeSetting (modifier: Modifier){
    val vm: AdvertisingViewModel = hiltViewModel()

    var mode by remember { mutableStateOf(vm.savedMode) }
    var txPower by remember { mutableStateOf(vm.savedTxPower) }
    var connectable by remember { mutableStateOf(vm.savedConnectable) }

    Column (
        modifier = Modifier
            .padding(16.dp)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){

        // === Settings Section ===
        Text("Advertising Data", style = MaterialTheme.typography.titleMedium)

        Column{
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


        }
    }
}