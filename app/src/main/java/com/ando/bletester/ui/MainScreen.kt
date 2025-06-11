package com.ando.bletester.ui

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ando.bletester.MainViewModel
import com.ando.bletester.NavRoutes
import com.ando.bletester.R
import com.ando.bletester.ui.theme.EndColor
import com.ando.bletester.ui.theme.MainBtnColor
import com.ando.bletester.ui.theme.MainImage
import com.ando.bletester.ui.theme.MainImageBg
import com.ando.bletester.ui.theme.MainText
import com.ando.bletester.ui.theme.allBgColor
import kotlin.system.exitProcess

@Composable
fun MainScreen(
    navController: NavController
){
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(allBgColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainLogo(
            Modifier.weight(0.3f)
        )
        MainMenu(
            navController,
            Modifier.weight(0.7f)
        )

    }
}

@Composable
fun MainLogo(
    modifier: Modifier
){
    val shape = RoundedCornerShape(10.dp)
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .background(MainImageBg,shape)
                .border(
                    width = 2.dp,
                    color = Color(0xFFB8EAFF),
                    shape = shape
                )
                .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Icon(
            painter = painterResource(id = R.drawable.bluetooth_main),
            contentDescription = "Bluetooth Icon",
            modifier = Modifier.size(64.dp),
            tint = MainImage
        )
        Text(
            text = "BLE Tester",
            fontSize = TextUnit(30f, TextUnitType.Sp),
            color = MainText
        )
    }
}

@Composable
fun MainMenu(
    navController : NavController,
    modifier : Modifier
){
    val activity = LocalActivity.current
    val vm : MainViewModel= hiltViewModel()

    Column (
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .padding(16.dp)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ColorButton(
            containerColor = MainBtnColor,
            text = "advertising"
        ){
            vm.safeNavigate {
                navController.navigate(NavRoutes.ADVERTISER)
            }
        }
//        Button(onClick = { /* TODO: 액션 2 */ }) {
//            Text("Extended Advertising")
//        }
        ColorButton(
            containerColor = MainBtnColor,
            text = "Scan"
        ) {
            vm.safeNavigate {
                navController.navigate(NavRoutes.SCANNER)
            }
        }

        ColorButton(
            containerColor = EndColor,
            text = "Exit"
        ) {
            vm.safeNavigate {
                vm.safeNavigate {
                    activity?.finishAffinity()
                    exitProcess(0)
                }
            }
        }
    }
}

@Composable
fun ColorButton(
    containerColor : Color = MainBtnColor,
    contentColor: Color = Color.White,
    text : String,
    onClick : () -> Unit,
){
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        onClick = onClick
    ) {
        Text(text)
    }
}
@Preview(showSystemUi = true)
@Composable
fun MainPreview(){
    MainScreen(rememberNavController())
}