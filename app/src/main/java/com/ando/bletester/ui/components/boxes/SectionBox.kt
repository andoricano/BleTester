package com.ando.bletester.ui.components.boxes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SectionLabelRow(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .then(modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .padding(12.dp) // 내부 여백
                .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            content()
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(y = (-10).dp)  // <- 위로 10dp 올림
                .padding(horizontal = 8.dp)
                .background(MaterialTheme.colorScheme.background)
        )
    }
}


@Composable
fun SectionLabelColumn(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .then(modifier)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .padding(12.dp) // 내부 여백
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(y = (-10).dp)  // <- 위로 10dp 올림
                .padding(horizontal = 8.dp)
                .background(MaterialTheme.colorScheme.background)
        )
    }
}