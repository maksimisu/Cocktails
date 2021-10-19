package com.maksimisu.cocktails

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: TheCocktailDBApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://thecocktaildb.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheCocktailDBApi::class.java)
    }

}