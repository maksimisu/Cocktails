package com.maksimisu.cocktails

import com.maksimisu.cocktails.data.Cocktail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TheCocktailDBApi {

    @GET("{path}")
    suspend fun getData(@Path("path") path: String): Response<List<Cocktail>>

}