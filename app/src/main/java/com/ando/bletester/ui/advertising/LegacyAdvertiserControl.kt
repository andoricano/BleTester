package com.ando.bletester.ui.advertising

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ando.bletester.ui.components.boxes.SectionLabelRow
import com.ando.bletester.ui.components.buttons.TextSwitchButton

@Composable
fun LegacyAdvertiserControl(
    modifier : Modifier = Modifier
){
    val vm: AdvertisingViewModel = hiltViewModel()
    var includeName by rememberSaveable { mutableStateOf(vm.includedName) }
    var includeTxPower by rememberSaveable { mutableStateOf(vm.includedTxPower) }
    var manufacturerIdText by remember { mutableIntStateOf(vm.includedManufacturerId) }
    var manufacturerDataText by remember { mutableStateOf(vm.includedManufacturerData) }

    Column(
        modifier = modifier
    ) {

        Text("Advertising Data", style = MaterialTheme.typography.titleMedium)
        SectionLabelRow(
            "include advertising",
            Modifier.height(IntrinsicSize.Min)
                .fillMaxWidth()
        ){
            TextSwitchButton("Name", includeName){checked ->
                includeName = checked
            }
            Spacer(modifier = Modifier.width(8.dp))

            TextSwitchButton("Tx Power", includeTxPower){checked ->
                includeTxPower = checked
            }
        }

        OutlinedTextField(
            value = manufacturerIdText.toString(),
            onValueChange = { manufacturerIdText = it.toIntOrNull()?:0 },
            label = { Text("Manufacturer ID (hex)") },
            singleLine = true
        )

        OutlinedTextField(
            value = manufacturerDataText,
            onValueChange = { manufacturerDataText = it },
            label = { Text("Manufacturer Data (hex)") },
            singleLine = true
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Button(onClick = {
            vm.configureLegacyAdvertisingData(
                includedName = includeName,
                includedTxPower = includeTxPower,
                manufacturerId = manufacturerIdText,
                manufacturerData = manufacturerDataText
            )
        }) {
            Text("Save")
        }

        Button(onClick = {
            vm.clearConfigData()
        }) {
            Text("Reset")
        }

    }
}