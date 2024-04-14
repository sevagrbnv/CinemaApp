package ru.sevagrbnv.cinemaapp.presentation.cinemaDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.sevagrbnv.cinemaapp.R
import ru.sevagrbnv.cinemaapp.databinding.PosterListItemBinding
import ru.sevagrbnv.cinemaapp.domain.models.Poster

class PosterAdapter:
    PagingDataAdapter<Poster, PosterAdapter.PosterViewHolder>(PosterComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val binding =
            PosterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PosterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    class PosterViewHolder(
        private val binding: PosterListItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(poster: Poster) {
            Picasso.get()
                .load(poster.urt)
                .placeholder(R.drawable.image_placeholder)
                .into(binding.poster)
        }
    }

    object PosterComparator : DiffUtil.ItemCallback<Poster>() {
        override fun areItemsTheSame(oldItem: Poster, newItem: Poster): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Poster, newItem: Poster): Boolean {
            return oldItem == newItem
        }
    }
}