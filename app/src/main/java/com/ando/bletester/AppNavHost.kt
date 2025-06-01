package com.ando.bletester

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ando.bletester.ui.scanned.ScannedItemScreen
import com.ando.bletester.ui.scanner.ScannerScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController(), modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = NavRoutes.SCANNER) {
        composable(NavRoutes.SCANNER) {
            ScannerScreen(navController = navController, modifier =modifier)
        }
        composable(NavRoutes.SCANNED) {
            ScannedItemScreen()
        }
    }
}