package com.maksimisu.cocktails

import com.maksimisu.cocktails.data.CocktailsHolder
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TheCocktailDBApi {

    @GET("/api/json/v1/{API_KEY}/filter.php?c=Ordinary_Drink")
    suspend fun getData(@Path("API_KEY") key: String): Response<CocktailsHolder>

}