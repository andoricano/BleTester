package com.ando.bletester.util


fun String.hexStringToByteArray(): ByteArray {
    val cleaned = this.uppercase().filter { it in '0'..'9' || it in 'A'..'F' }

    if (cleaned.length < 2) return byteArrayOf()

    val evenLengthStr = if (cleaned.length % 2 == 0) cleaned else cleaned.dropLast(1)

    return evenLengthStr.chunked(2)
        .map { it.toInt(16).toByte() }
        .toByteArray()
}