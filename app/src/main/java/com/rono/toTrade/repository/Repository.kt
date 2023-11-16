package com.rono.toTrade.repository

import com.rono.toTrade.dataSources.constants.ConstantsDataSource
import com.rono.toTrade.dataSources.dataStore.DataStoreRepo
import com.rono.toTrade.dataSources.local.LocalDataSource
import com.rono.toTrade.dataSources.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource,
    constantsDataSource: ConstantsDataSource,
    dataStoreRepo: DataStoreRepo
) {
    val remote = remoteDataSource
    val constants = constantsDataSource
    val dataStoreRepository = dataStoreRepo
    val local = localDataSource
}