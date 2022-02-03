package com.gmail.orlandroyd.myapplication.repository

import com.gmail.orlandroyd.myapplication.model.Character
import com.gmail.orlandroyd.myapplication.networking.BaseDataSource
import com.gmail.orlandroyd.myapplication.networking.CharacterApi
import com.gmail.orlandroyd.myapplication.networking.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharacterRepo @Inject constructor(
    private val apiService: CharacterApi
) : DefaultCharacterRepo, BaseDataSource() {

    override suspend fun fetchCharacters(): Flow<DataState<List<Character>>> =
        flow {
            val response = safeApiCall { apiService.fetchCharacters() }
            emit(response)
        }.flowOn(Dispatchers.IO)

}