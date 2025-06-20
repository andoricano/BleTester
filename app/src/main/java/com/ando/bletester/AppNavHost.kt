package com.ando.bletester

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ando.bletester.ui.MainScreen
import com.ando.bletester.ui.advertising.LegacyAdvertiserControlScreen
import com.ando.bletester.ui.advertising.LegacyAdvertiserScreen
import com.ando.bletester.ui.advertising.LegacyAdvertiserSettingScreen
import com.ando.bletester.ui.scanned.ScannedItemScreen
import com.ando.bletester.ui.scanner.ScannerScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController(), modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = NavRoutes.MAIN) {
        composable(NavRoutes.MAIN){
            MainScreen(navController)
        }
        composable(NavRoutes.SCANNER) {
            ScannerScreen(navController = navController, modifier =modifier)
        }
        composable(NavRoutes.SCANNED) {
            ScannedItemScreen()
        }

        composable(NavRoutes.ADVERTISER){
            LegacyAdvertiserScreen(navController)
        }

        composable(NavRoutes.LEGACY_SETTING){
            LegacyAdvertiserSettingScreen(navController)
        }
        composable(NavRoutes.LEGACY_CONTROL){
            LegacyAdvertiserControlScreen()
        }
    }
}