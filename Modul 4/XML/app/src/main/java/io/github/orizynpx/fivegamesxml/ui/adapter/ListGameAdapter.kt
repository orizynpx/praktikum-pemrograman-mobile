package io.github.orizynpx.fivegamesxml.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.orizynpx.fivegamesxml.data.model.Game
import io.github.orizynpx.fivegamesxml.databinding.ItemGameListBinding

class ListGameAdapter(
    private val onDetailClick: (Game) -> Unit,
    private val onLinkClick: (String) -> Unit
) : ListAdapter<Game, ListGameAdapter.GameViewHolder>(DiffCallback) {

    class GameViewHolder(private val binding: ItemGameListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(game: Game, onDetailClick: (Game) -> Unit, onLinkClick: (String) -> Unit) {
            val context = binding.root.context

            binding.imgGame.setImageResource(game.imageResourceId)
            binding.tvTitle.text = context.getString(game.titleResourceId)
            binding.tvYear.text = context.getString(game.yearResourceId)
            binding.tvGenre.text = context.getString(game.genreResourceId)

            binding.btnDetail.setOnClickListener { onDetailClick(game) }

            binding.btnLink.setOnClickListener {
                val urlString = context.getString(game.url)
                onLinkClick(urlString)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemGameListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position), onDetailClick, onLinkClick)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean = oldItem == newItem
    }
}