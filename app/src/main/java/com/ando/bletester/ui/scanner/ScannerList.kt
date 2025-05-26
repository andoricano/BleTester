package com.ando.bletester.ui.scanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ScannerList(
    modifier: Modifier = Modifier,
    viewModel : ScannerViewModel = hiltViewModel()
){
    val scanResults by viewModel.scanResults

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("스캐너 화면", style = MaterialTheme.typography.headlineSmall)

        Button(onClick = { viewModel.startScan() }) {
            Text("스캔하기")
        }

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn {
            items(scanResults) { result ->
                ScannerListItem(result)
            }
        }
    }
}