package com.ando.bletester.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ando.bletester.NavRoutes

@Composable
fun MainScreen(
    navController: NavController
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate(NavRoutes.ADVERTISER)}) {
            Text("Advertising")
        }
//        Button(onClick = { /* TODO: 액션 2 */ }) {
//            Text("Extended Advertising")
//        }
//        Button(onClick = { /* TODO: 액션 3 */ }) {
//            Text("버튼 3")
//        }
    }
}