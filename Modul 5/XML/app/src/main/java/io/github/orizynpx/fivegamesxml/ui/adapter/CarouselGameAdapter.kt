package io.github.orizynpx.fivegamesxml.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import io.github.orizynpx.fivegamesxml.R
import io.github.orizynpx.fivegamesxml.data.local.entity.MovieEntity
import io.github.orizynpx.fivegamesxml.databinding.ItemGameCarouselBinding

class CarouselGameAdapter(private val onClick: (MovieEntity) -> Unit) :
    ListAdapter<MovieEntity, CarouselGameAdapter.ViewHolder>(ListGameAdapter.DiffCallback) {

    class ViewHolder(val binding: ItemGameCarouselBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGameCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        val imageUrl = "https://image.tmdb.org/t/p/w780${movie.backdropPath}"
        
        holder.binding.imgCarousel.load(imageUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_background)
        }
        
        holder.binding.imgCarousel.setOnClickListener {
            onClick(movie)
        }
    }
}
