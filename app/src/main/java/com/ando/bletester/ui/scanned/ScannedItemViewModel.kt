package com.ando.bletester.ui.scanned

import androidx.lifecycle.ViewModel
import com.ando.bletester.data.repository.scan.BleScannerRepository
import com.ando.bletester.ui.scanner.data.ScannerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScannedItemViewModel  @Inject constructor(
    private val scanRepo: BleScannerRepository
) : ViewModel(){

    fun connectBle(scannerItem: ScannerItem){
        scanRepo.connectBleByIndex(scannerItem)
    }
    fun testConnection(idx : Int){
        scanRepo.testConnection(idx)
    }

}