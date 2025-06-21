package com.ando.bletester.ui.advertising

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LegacyAdvertiserScreen(
    navController: NavController
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 15.dp)
    ) {
        LegacyAdvertiserSettingScreen(navController)
    }
}