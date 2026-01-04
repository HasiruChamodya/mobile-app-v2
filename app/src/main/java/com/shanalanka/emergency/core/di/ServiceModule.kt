package com.shanalanka.emergency.core.di

import android.content.Context
import android.content.SharedPreferences
import com.shanalanka.emergency.domain.service.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing emergency services.
 */
@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    
    @Provides
    @Singleton
    fun provideLocationService(
        @ApplicationContext context: Context
    ): LocationService {
        return LocationServiceImpl(context)
    }
    
    @Provides
    @Singleton
    fun provideSmsService(
        @ApplicationContext context: Context
    ): SmsService {
        return SmsServiceImpl(context)
    }
    
    @Provides
    @Singleton
    fun providePermissionManager(
        @ApplicationContext context: Context
    ): PermissionManager {
        return PermissionManager(context)
    }
    
    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("emergency_prefs", Context.MODE_PRIVATE)
    }
    
    @Provides
    @Singleton
    fun provideEmergencyTriggerManager(
        locationService: LocationService,
        smsService: SmsService,
        contactRepository: com.shanalanka.emergency.data.repository.ContactRepository
    ): EmergencyTriggerManager {
        return EmergencyTriggerManager(locationService, smsService, contactRepository)
    }
}
