package com.maksimisu.cocktails.ui.fragments

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
import com.maksimisu.cocktails.data.Cocktail
import com.maksimisu.cocktails.databinding.FragmentOrdinaryDrinksBinding
import com.maksimisu.cocktails.ui.CocktailsListAdapter
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class OrdinaryDrinksFragment : Fragment(R.layout.fragment_ordinary_drinks) {

    private lateinit var binding: FragmentOrdinaryDrinksBinding
    private lateinit var cocktailsListAdapter: CocktailsListAdapter

    private lateinit var cocktailsList: MutableList<Cocktail>

    private val TAG_NETWORKING = "NETWORKING"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        cocktailsList = mutableListOf()

        view.apply {
            binding = FragmentOrdinaryDrinksBinding.inflate(layoutInflater)

            binding.rvOrdinaryDrinks.apply {
                cocktailsListAdapter = CocktailsListAdapter()
                adapter = cocktailsListAdapter
                layoutManager = LinearLayoutManager(this.context)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            viewLifecycleOwner.lifecycleScope.launch {
            Log.i(TAG_NETWORKING, "In lifecycleScope")
            val response = try {
                RetrofitInstance.api.getData("1")
            } catch (e: IOException) {
                Log.e(TAG_NETWORKING, "IOException")
                return@launch
            } catch (e: HttpException) {
                Log.e(TAG_NETWORKING, "HttpException")
                return@launch
            }
            if(response.isSuccessful && response.body() != null) {
                cocktailsListAdapter.cocktailsList = response.body()!!.cocktails
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