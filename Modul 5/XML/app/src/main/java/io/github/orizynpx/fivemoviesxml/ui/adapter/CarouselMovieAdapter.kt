package io.github.orizynpx.fivemoviesxml.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import io.github.orizynpx.fivemoviesxml.R
import io.github.orizynpx.fivemoviesxml.data.local.entity.MovieEntity
import io.github.orizynpx.fivemoviesxml.databinding.ItemMovieCarouselBinding

class CarouselMovieAdapter(private val onClick: (MovieEntity) -> Unit) :
    ListAdapter<MovieEntity, CarouselMovieAdapter.ViewHolder>(ListMovieAdapter.DiffCallback) {

    class ViewHolder(val binding: ItemMovieCarouselBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMovieCarouselBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        val imageUrl = movie.backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" }

        holder.binding.imgCarousel.load(imageUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_background)
        }

        holder.binding.imgCarousel.setOnClickListener {
            onClick(movie)
        }
    }
}
