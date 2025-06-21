package com.ando.bletester.ui.components.buttons

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextSwitchButton(
    text : String,
    checked : Boolean,
    onClick : (Boolean) -> Unit
){
    var switchData by remember { mutableStateOf(checked) }

    Row(
        modifier = Modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text)
        Spacer(modifier = Modifier.width(4.dp))
        Switch(checked = switchData, onCheckedChange = {
            onClick(it)
            switchData = it
        })
    }
}