package com.example.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.core.R
import com.example.core.databinding.FavoriteItemLayoutBinding
import com.example.core.domain.model.Favorite
import com.example.core.domain.model.Movie

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val favorites = mutableListOf<Favorite>()
    private lateinit var onItemClick: (favorite: Favorite) -> Unit

    fun setOnItemClick(onItemClick: (favorite: Favorite) -> Unit) {
        this.onItemClick = onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemBinding =
            FavoriteItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favorites[position]
        holder.bind(favorite)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    fun setDataSet(favorites: List<Favorite>?) {
        if (favorites == null) return
        this.favorites.clear()
        this.favorites.addAll(favorites)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val itemBinding: FavoriteItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(favoriteData: Favorite) {
            with(itemBinding) {
                favorite = favoriteData
                cvFilm.setOnClickListener { onItemClick.invoke(favoriteData) }
                Glide.with(root)
                    .load(favoriteData.image)
                    .apply(RequestOptions.errorOf(R.drawable.ic_error_white))
                    .into(ivThumbnail)
            }
        }
    }
}