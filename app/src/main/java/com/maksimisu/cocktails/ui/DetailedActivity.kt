package com.maksimisu.cocktails.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.maksimisu.cocktails.RetrofitInstance
import com.maksimisu.cocktails.data.Cocktail
import com.maksimisu.cocktails.data.Constants
import com.maksimisu.cocktails.databinding.ActivityDetailedBinding
import com.squareup.picasso.Picasso
import retrofit2.HttpException
import java.io.IOException

class DetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedBinding
    private lateinit var drink: Cocktail
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra("EXTRA_ID").orEmpty()

        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getDrinkById(id)
            } catch (e: IOException) {
                Log.e(Constants.TAG_NETWORKING, "IOException")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(Constants.TAG_NETWORKING, "HttpException")
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body() != null) {
                drink = response.body()!!.drinks[0]
                runOnUiThread {
                    binding.tvDetailedName.text = drink.strDrink
                    binding.tvDetailedCategory.text = drink.strCategory
                    binding.tvDetailedInstructions.text = drink.strInstructions
                    Picasso.with(this@DetailedActivity).load(drink.strDrinkThumb).into(binding.ivDetailedIcon)
                }
            } else {
                Log.d(Constants.TAG_NETWORKING, "Something went wrong.")
            }
        }
    }
}