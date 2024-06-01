package com.example.taskmanager.model.service.module

import com.example.taskmanager.model.service.AccountService
import com.example.taskmanager.model.service.ConfigurationService
import com.example.taskmanager.model.service.LogService
import com.example.taskmanager.model.service.StorageService
import com.example.taskmanager.model.service.impl.AccountServiceImpl
import com.example.taskmanager.model.service.impl.ConfigurationServiceImpl
import com.example.taskmanager.model.service.impl.LogServiceImpl
import com.example.taskmanager.model.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService
    @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
    @Binds abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService
}