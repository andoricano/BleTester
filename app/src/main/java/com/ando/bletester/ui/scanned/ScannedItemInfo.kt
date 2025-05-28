package com.ando.bletester.ui.scanned

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ando.bletester.ui.scanner.data.ScannerItem

@Composable
fun ScannedItemInfo(scannerItem: ScannerItem, modifier : Modifier){
    Column(modifier = Modifier.padding(26.dp)
        .then(modifier)) {
        Text(text = "${scannerItem.deviceName}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = scannerItem.address)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "${scannerItem.rssi}")
    }
}