package com.ando.bletester.ui.scanned

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ando.bletester.MainViewModel
import com.ando.bletester.ui.components.buttons.BottomButton

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