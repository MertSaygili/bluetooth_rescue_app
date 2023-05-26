package com.plcoding.bluetoothchat.di

import android.content.Context
import com.plcoding.bluetoothchat.data.chat.AndroidBluetoothController
import com.plcoding.bluetoothchat.data.chat.SOSMessageBluetoothController
import com.plcoding.bluetoothchat.domain.chat.BluetoothController
import com.plcoding.bluetoothchat.domain.chat.SOSMessageController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideBluetoothController(@ApplicationContext context: Context): BluetoothController {
        return AndroidBluetoothController(context)
    }

    @Provides
    @Singleton
    fun provideSOSBluetoothController(@ApplicationContext context: Context): SOSMessageController {
        return SOSMessageBluetoothController(context)
    }
}
