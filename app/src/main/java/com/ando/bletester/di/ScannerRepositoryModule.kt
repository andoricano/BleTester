package com.ando.bletester.di

import android.bluetooth.BluetoothManager
import android.content.Context
import com.ando.bletester.bluetooth.ble.advertiser.AdvertiserManager
import com.ando.bletester.bluetooth.ble.scanner.BleGattClient
import com.ando.bletester.bluetooth.ble.scanner.ScannerManager
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