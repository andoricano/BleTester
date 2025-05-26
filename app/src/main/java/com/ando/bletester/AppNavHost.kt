package com.ando.bletester

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ando.bletester.ui.scanned.ScannedItemScreen
import com.ando.bletester.ui.scanner.ScannerScreen
import com.ando.bletester.ui.scanner.data.ScannerItem

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController(), modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = NavRoutes.SCANNER) {
        composable(NavRoutes.SCANNER) {
            ScannerScreen(navController = navController, modifier =modifier)
        }
        composable(NavRoutes.SCANNED) { backStackEntry ->
            val scannedItem = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<ScannerItem>(NavArgs.SCANNER_ITEM)
            ScannedItemScreen(scannedItem)
        }
    }
}