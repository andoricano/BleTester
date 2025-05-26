package com.ando.bletester.ui.scanner

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ando.bletester.NavArgs
import com.ando.bletester.NavRoutes
import com.ando.bletester.ui.scanner.data.ScannerItem

@Composable
fun ScannerScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: ScannerViewModel = hiltViewModel()
    val onClick : (ScannerItem) -> Unit ={item ->
        navController.currentBackStackEntry?.savedStateHandle?.set(NavArgs.SCANNER_ITEM, item)
        navController.navigate(NavRoutes.SCANNED)

        Log.d("ScannerScreen", "Saved scannerItem: $item")
    }
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        ScannerList(onClick = onClick)
    }
}
