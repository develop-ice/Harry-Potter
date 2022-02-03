package com.gmail.orlandroyd.myapplication.repository

import com.gmail.orlandroyd.myapplication.model.Character
import com.gmail.orlandroyd.myapplication.networking.DataState
import kotlinx.coroutines.flow.Flow

interface DefaultCharacterRepo {

    suspend fun fetchCharacters(): Flow<DataState<List<Character>>>

}