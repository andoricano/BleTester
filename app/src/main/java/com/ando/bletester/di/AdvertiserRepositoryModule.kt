package com.ando.bletester.di

import android.bluetooth.BluetoothManager
import android.content.Context
import com.ando.bletester.bluetooth.ble.advertiser.AdvertiserManager
import com.ando.bletester.bluetooth.ble.advertiser.BleGattServer
import com.ando.bletester.bluetooth.ble.scanner.BleGattClient
import com.ando.bletester.data.repository.advertising.BleAdvertisingRepository
import com.ando.bletester.data.repository.advertising.BleAdvertisingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AdvertiserRepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(
        @ApplicationContext context : Context,
        bleGattServer: BleGattServer
    ) : BleAdvertisingRepository {
        val btManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

        return BleAdvertisingRepositoryImpl(
            AdvertiserManager(btManager),
            bleGattServer
        )
    }

}