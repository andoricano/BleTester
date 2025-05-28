package com.ando.bletester.ui.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomButton(text : String,modifier : Modifier, onClick : () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF3C8EF3) // bluetooth니까 blue
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // 버튼 높이
            .padding(horizontal = 16.dp)
            .then(modifier)
    ) {
        Text(text = text, color = Color.White)
    }
}