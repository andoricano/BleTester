package com.ando.bletester.ui.scanned

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ando.bletester.ui.scanner.data.ScannerItem

@Composable
fun ScannedItemScreen(scannerItem: ScannerItem?) {
    Log.i("ScannedItemScreen", "$scannerItem")
    val viewModel: ScannedItemViewModel = hiltViewModel()
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "${scannerItem?.deviceName}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "${scannerItem?.address}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "${scannerItem?.rssi}")
    }
}