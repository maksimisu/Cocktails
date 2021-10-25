package com.maksimisu.cocktails.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maksimisu.cocktails.data.Cocktail
import com.maksimisu.cocktails.databinding.ItemCoktailBinding

class CocktailsListAdapter : RecyclerView.Adapter<CocktailsListAdapter.CocktailsListViewHolder>() {

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
        holder.bind()
    }

    override fun getItemCount(): Int {
        return cocktailsList.size
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Cocktail>() {
        override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var cocktailsList: List<Cocktail>
    get() = differ.currentList
    set(value) { differ.submitList(value) }

    inner class CocktailsListViewHolder(
        private val binding: ItemCoktailBinding,
        listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener.onClick(adapterPosition)
            }
        }

        fun bind() = with(binding) {
            val cocktail = cocktailsList[adapterPosition]
            tvName.text = cocktail.strDrink
        }

    }

}