package com.maksimisu.cocktails.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maksimisu.cocktails.data.Cocktail
import com.maksimisu.cocktails.databinding.ItemCoktailBinding

class CocktailsListAdapter(
    private val cocktailsList: MutableList<Cocktail>
) : RecyclerView.Adapter<CocktailsListAdapter.CocktailsListViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailsListViewHolder {
        return CocktailsListViewHolder(
            ItemCoktailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: CocktailsListViewHolder, position: Int) {
        holder.bind(cocktailsList[position])
    }

    override fun getItemCount(): Int {
        return cocktailsList.size
    }

    class CocktailsListViewHolder(
        private val binding: ItemCoktailBinding,
        listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener.onClick(adapterPosition)
            }
        }

        fun bind(item: Cocktail) = with(binding) {
            tvName.text = item.strDrink
        }

    }

}