package io.github.orizynpx.fivemoviesxml.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import io.github.orizynpx.fivemoviesxml.R
import io.github.orizynpx.fivemoviesxml.data.local.entity.MovieEntity
import io.github.orizynpx.fivemoviesxml.databinding.ItemMovieListBinding

class ListMovieAdapter(
    private val onDetailClick: (MovieEntity) -> Unit
) : ListAdapter<MovieEntity, ListMovieAdapter.MovieViewHolder>(DiffCallback) {

    class MovieViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieEntity, onDetailClick: (MovieEntity) -> Unit) {
            binding.tvTitle.text = movie.title
            binding.tvYear.text = movie.releaseDate
            binding.tvGenre.text = movie.overview

            val imageUrl = movie.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
            binding.imgMovie.load(imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_launcher_background)
            }

            binding.btnDetail.setOnClickListener { onDetailClick(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position), onDetailClick)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean = 
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean = 
            oldItem == newItem
    }
}
