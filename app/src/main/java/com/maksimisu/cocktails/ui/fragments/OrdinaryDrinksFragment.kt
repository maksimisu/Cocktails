package com.maksimisu.cocktails.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.maksimisu.cocktails.R
import com.maksimisu.cocktails.RetrofitInstance
import com.maksimisu.cocktails.data.CocktailItem
import com.maksimisu.cocktails.data.Constants.Companion.TAG_NETWORKING
import com.maksimisu.cocktails.databinding.FragmentOrdinaryDrinksBinding
import com.maksimisu.cocktails.ui.CocktailsListAdapter
import com.maksimisu.cocktails.ui.DetailedActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.HttpException
import java.io.IOException

@DelicateCoroutinesApi
class OrdinaryDrinksFragment : Fragment(R.layout.fragment_ordinary_drinks) {

    private lateinit var binding: FragmentOrdinaryDrinksBinding
    private lateinit var cocktailsListAdapter: CocktailsListAdapter
    private lateinit var cocktailsList: MutableList<CocktailItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        cocktailsList = mutableListOf()

        binding = FragmentOrdinaryDrinksBinding.inflate(layoutInflater)

        val view = binding.root

        binding.rvOrdinaryDrinks.apply {
            cocktailsListAdapter = CocktailsListAdapter(this.context)
            adapter = cocktailsListAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getOrdinaryDrinks()
            } catch (e: IOException) {
                Log.e(TAG_NETWORKING, "IOException")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG_NETWORKING, "HttpException")
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body() != null) {
                cocktailsListAdapter.cocktailsList = response.body()!!.drinks
            } else {
                Log.d(TAG_NETWORKING, "Something went wrong.")
            }
        }

        cocktailsListAdapter.setOnItemClickListener(object :
            CocktailsListAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                startActivity(Intent(activity, DetailedActivity::class.java).also {
                    it.putExtra("EXTRA_ID", cocktailsListAdapter.cocktailsList[position].idDrink)
                })
            }
        })

        binding.ordinaryDrinksRefresher.setOnRefreshListener {
            lifecycleScope.launchWhenCreated {
                val response = try {
                    RetrofitInstance.api.getOrdinaryDrinks().also {
                        binding.ordinaryDrinksRefresher.isRefreshing = false
                    }
                } catch (e: IOException) {
                    Log.e(TAG_NETWORKING, "IOException")
                    return@launchWhenCreated
                } catch (e: HttpException) {
                    Log.e(TAG_NETWORKING, "HttpException")
                    return@launchWhenCreated
                }
                if(response.isSuccessful && response.body() != null) {
                    cocktailsListAdapter.cocktailsList = response.body()!!.drinks
                } else {
                    Log.d(TAG_NETWORKING, "Something went wrong.")
                }
            }
        }
    }

}