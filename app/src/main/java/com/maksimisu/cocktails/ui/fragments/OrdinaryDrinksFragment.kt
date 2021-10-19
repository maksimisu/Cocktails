package com.maksimisu.cocktails.ui.fragments

import android.os.Bundle
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
import retrofit2.Response

class OrdinaryDrinksFragment : Fragment(R.layout.fragment_ordinary_drinks) {

    private lateinit var binding: FragmentOrdinaryDrinksBinding
    private lateinit var cocktailsListAdapter: CocktailsListAdapter

    private lateinit var cocktailsList: MutableList<Cocktail>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        cocktailsList = mutableListOf()

        lifecycleScope.launchWhenCreated {
            val response: Response<List<Cocktail>> = try {
                RetrofitInstance.api.getData("/api/json/v1/1/filter.php?c=Ordinary_Drink")
            } finally {

            }

            if(response.isSuccessful && response.body() != null) {
                for(i in response.body()!!) {
                    cocktailsList.add(i)
                }
            }
        }

        binding = FragmentOrdinaryDrinksBinding.inflate(layoutInflater)

        cocktailsListAdapter = CocktailsListAdapter(cocktailsList)
        binding.rvOrdinaryDrinks.adapter = cocktailsListAdapter
        binding.rvOrdinaryDrinks.layoutManager = LinearLayoutManager(this.context)

        cocktailsListAdapter.setOnItemClickListener(object :
            CocktailsListAdapter.OnItemClickListener {
            override fun onClick(position: Int) {

            }
        })

        return null
    }

}