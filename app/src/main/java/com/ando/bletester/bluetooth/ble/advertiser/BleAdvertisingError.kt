package com.ando.bletester.bluetooth.ble.advertiser

enum class BleAdvertisingError {
    NONE,
    SUCCESS,
    ALREADY_STARTED,
    TOO_MANY_ADVERTISERS,
    INTERNAL_ERROR,
    DATA_TOO_LARGE,
    FEATURE_UNSUPPORTED,
    UNKNOWN;

    companion object {
        fun fromStatus(status: Int): BleAdvertisingError = when(status) {
            -1234 -> NONE
            0 -> SUCCESS
            3 -> ALREADY_STARTED
            2 -> TOO_MANY_ADVERTISERS
            4 -> INTERNAL_ERROR
            1 -> DATA_TOO_LARGE
            5 -> FEATURE_UNSUPPORTED
            else -> UNKNOWN
        }
    }
}