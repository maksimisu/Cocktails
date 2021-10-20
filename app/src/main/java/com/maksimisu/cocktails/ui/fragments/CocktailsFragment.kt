package com.maksimisu.cocktails.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.maksimisu.cocktails.R
import com.maksimisu.cocktails.RetrofitInstance
import com.maksimisu.cocktails.data.Cocktail
import com.maksimisu.cocktails.data.Constants.Companion.TAG_NETWORKING
import com.maksimisu.cocktails.databinding.FragmentCocktailsBinding
import com.maksimisu.cocktails.ui.CocktailsListAdapter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@DelicateCoroutinesApi
class CocktailsFragment : Fragment(R.layout.fragment_cocktails) {

    private lateinit var binding: FragmentCocktailsBinding
    private lateinit var cocktailsListAdapter: CocktailsListAdapter

    private lateinit var cocktailsList: MutableList<Cocktail>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        cocktailsList = mutableListOf()

        view.apply {
            binding = FragmentCocktailsBinding.inflate(layoutInflater)

            cocktailsListAdapter = CocktailsListAdapter()
            binding.rvCocktails.adapter = cocktailsListAdapter
            binding.rvCocktails.layoutManager = LinearLayoutManager(this?.context)
        }

        return this.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getCocktails("1")
            } catch (e: IOException) {
                Log.e(TAG_NETWORKING, "IOException")
                return@launch
            } catch (e: HttpException) {
                Log.e(TAG_NETWORKING, "HttpException")
                return@launch
            }

            if(response.isSuccessful && response.body() != null) {
                cocktailsListAdapter.cocktailsList = response.body()!!.drinks
                cocktailsListAdapter.notifyDataSetChanged()
            } else {
                Log.d(TAG_NETWORKING, "Something went wrong.")
            }
        }

        cocktailsListAdapter.setOnItemClickListener(object :
            CocktailsListAdapter.OnItemClickListener {
            override fun onClick(position: Int) {

            }
        })
    }

}