package com.ando.bletester.ui.scanned

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@SuppressLint("MissingPermission")
@Composable
fun ScannedItemInfo(scannerDevice : BluetoothDevice,modifier : Modifier){
    Column(modifier = Modifier.padding(26.dp)
        .then(modifier)) {
        Text(text = "${scannerDevice.name}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = scannerDevice.address)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "${scannerDevice.type}")
    }
}