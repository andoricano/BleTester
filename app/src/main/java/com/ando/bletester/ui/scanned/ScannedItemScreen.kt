package com.ando.bletester.ui.scanned

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ando.bletester.App.Companion.TAG
import com.ando.bletester.MainViewModel
import com.ando.bletester.ui.components.buttons.BottomButton
import com.ando.bletester.ui.scanner.data.ScannerItem
import java.nio.file.WatchEvent

@Composable
fun ScannedItemScreen() {
    val viewModel: ScannedItemViewModel = hiltViewModel()
    val mvm : MainViewModel = hiltViewModel()
    val device = mvm.selectedDevice
    if(device == null) return

    Column(
        modifier = Modifier
            .padding(26.dp)
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        ScannedItemInfo(
            device,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        BottomButton(
            text = "connection",
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            //TODO
        }
    }
}