package com.example.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.core.R
import com.example.core.databinding.MovieItemLayoutBinding
import com.example.core.domain.model.Movie

class MovieAdapter() : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movies = mutableListOf<Movie>()
    private lateinit var onItemClick: (movie: Movie) -> Unit

    fun setOnItemClick(onItemClick: (movie: Movie) -> Unit) {
        this.onItemClick = onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding =
            MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movies = movies[position]
        holder.bind(movies)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setDataSet(movies: List<Movie>?) {
        if (movies == null) return
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(private val itemBinding: MovieItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(movieData: Movie) {
            with(itemBinding) {
                movie = movieData
                cvFilm.setOnClickListener { onItemClick.invoke(movieData) }
                Glide.with(root)
                    .load(movieData.image)
                    .apply(RequestOptions.errorOf(R.drawable.ic_error_white))
                    .into(ivThumbnail)
            }
        }
    }
}