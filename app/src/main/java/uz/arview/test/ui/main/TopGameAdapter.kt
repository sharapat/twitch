package uz.arview.test.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.arview.test.R
import uz.arview.test.core.inflate
import uz.arview.test.data.local.model.GameDbModel
import uz.arview.test.databinding.ItemTopGameBinding

class TopGameAdapter : RecyclerView.Adapter<TopGameAdapter.TopGameViewHolder>() {

    var games: MutableList<GameDbModel> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addGames(list: List<GameDbModel>) {
        games.addAll(list)
        notifyDataSetChanged()
    }

    inner class TopGameViewHolder(private val binding: ItemTopGameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(game: GameDbModel) {
            binding.apply {
                Glide.with(binding.root.context).load(game.boxLarge).centerCrop().into(box)
                name.text = game.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopGameViewHolder {
        val itemView = parent.inflate(R.layout.item_top_game)
        val binding = ItemTopGameBinding.bind(itemView)
        return TopGameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopGameViewHolder, position: Int) {
        holder.populateModel(games[position])
    }

    override fun getItemCount() = games.size
}