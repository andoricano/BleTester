package com.ando.bletester.ui.scanner.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScannerItem(
    val idx : Int,
    val deviceName: String,
    val address: String,
    val rssi: Int
) : Parcelable {
    companion object {
        fun empty() = ScannerItem(
            idx = -1,
            deviceName = "",
            address = "",
            rssi = 0
        )
    }
}