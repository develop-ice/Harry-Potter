package com.gmail.orlandroyd.myapplication.networking

import com.gmail.orlandroyd.myapplication.model.Character
import retrofit2.Response
import retrofit2.http.GET

interface CharacterApi {

    @GET(ApiConstants.CHARACTER_END_POINTS)
    suspend fun fetchCharacters(): Response<List<Character>>

}