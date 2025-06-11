package com.ando.bletester

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.ando.bletester.data.repository.scan.BleScannerRepository
import com.ando.bletester.ui.scanner.data.ScannerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val scanRepo: BleScannerRepository
) : ViewModel(){
    private var navigationJob: Job? = null
    var selectedDevice : BluetoothDevice? = null

    fun updateSelectedDevice(scannerItem: ScannerItem){
        selectedDevice = scanRepo.getFindScannedDeviceInfo(scannerItem)
    }


    fun safeNavigate(action : () -> Unit) {
        if (navigationJob?.isActive == true) return

        navigationJob = viewModelScope.launch {
            action()
            delay(1000)
        }
    }
}