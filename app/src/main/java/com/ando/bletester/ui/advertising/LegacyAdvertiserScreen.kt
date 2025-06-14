package com.ando.bletester.ui.advertising

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun LegacyAdvertiserScreen(
    navController: NavController,
    modifier: Modifier = Modifier
){

    Column(modifier = Modifier.fillMaxSize()) {
        BtDeviceName()
        LegacyAdvertiserControl()
    }
}