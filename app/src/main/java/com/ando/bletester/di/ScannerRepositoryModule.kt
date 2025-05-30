package com.ando.bletester.di

import android.app.Application
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.content.Context
import com.ando.bletester.ble.advertiser.AdvertiserManager
import com.ando.bletester.ble.scanner.BleGattClient
import com.ando.bletester.ble.scanner.ScannerManager
import com.ando.bletester.data.repository.scan.BleScannerRepository
import com.ando.bletester.data.repository.scan.BleScannerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScannerRepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(
        @ApplicationContext context : Context,
        bleGattClient: BleGattClient
    ) : BleScannerRepository {
        val btManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

        return BleScannerRepositoryImpl(
            ScannerManager(btManager.adapter.bluetoothLeScanner),
            AdvertiserManager(btManager),
            bleGattClient
        )
    }

}