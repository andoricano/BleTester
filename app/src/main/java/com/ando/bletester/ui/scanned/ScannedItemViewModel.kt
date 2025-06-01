package com.ando.bletester.ui.scanned

import androidx.lifecycle.ViewModel
import com.ando.bletester.data.repository.scan.BleScannerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScannedItemViewModel  @Inject constructor(
    private val scanRepo: BleScannerRepository
) : ViewModel(){

    fun testConnection(device : String, connection : Boolean){
        scanRepo.testConnection(device,connection)
    }

}