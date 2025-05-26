package com.ando.bletester.ui.scanner.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScannerItem(
    val deviceName: String?,
    val address: String,
    val rssi: Int
) : Parcelable