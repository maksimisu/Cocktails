package com.maksimisu.cocktails

import com.maksimisu.cocktails.data.CocktailsHolder
import com.maksimisu.cocktails.data.CocktailsItemHolder
import com.maksimisu.cocktails.data.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheCocktailDBApi {

    @GET("api/json/v1/$API_KEY/filter.php?c=Ordinary_Drink")
    suspend fun getOrdinaryDrinks(): Response<CocktailsItemHolder>

    @GET("api/json/v1/$API_KEY/filter.php?c=Cocktail")
    suspend fun getCocktails(): Response<CocktailsItemHolder>

    @GET("api/json/v1/$API_KEY/lookup.php")
    suspend fun getDrinkById(@Query("i") id: String): Response<CocktailsHolder>

}