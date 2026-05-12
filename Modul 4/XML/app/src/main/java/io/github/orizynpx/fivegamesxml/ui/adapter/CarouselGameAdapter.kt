package io.github.orizynpx.fivegamesxml.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.orizynpx.fivegamesxml.data.model.Game
import io.github.orizynpx.fivegamesxml.databinding.ItemGameCarouselBinding

class CarouselGameAdapter(private val onClick: (Game) -> Unit) :
    ListAdapter<Game, CarouselGameAdapter.ViewHolder>(ListGameAdapter.DiffCallback) {

    class ViewHolder(val binding: ItemGameCarouselBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGameCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = getItem(position)
        holder.binding.imgCarousel.setImageResource(game.imageResourceId)
        holder.binding.imgCarousel.setOnClickListener {
            onClick(game)
        }
    }
}