package com.ando.bletester.di

import com.ando.bletester.ble.scanner.BleGattClient
import com.ando.bletester.data.repository.scan.BleScannerRepository
import com.ando.bletester.data.repository.scan.BleScannerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScannerRepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(bleGattClient: BleGattClient) : BleScannerRepository{
        return BleScannerRepositoryImpl(bleGattClient)
    }

}