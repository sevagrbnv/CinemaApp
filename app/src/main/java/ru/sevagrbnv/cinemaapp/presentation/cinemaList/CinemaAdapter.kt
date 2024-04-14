package ru.sevagrbnv.cinemaapp.presentation.cinemaList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.sevagrbnv.cinemaapp.R
import ru.sevagrbnv.cinemaapp.databinding.CinemaListItemBinding
import ru.sevagrbnv.cinemaapp.domain.models.Cinema

class CinemaAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Cinema, CinemaAdapter.CinemaViewHolder>(CinemaComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {
        val binding =
            CinemaListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CinemaViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    class CinemaViewHolder(
        private val binding: CinemaListItemBinding,
        private val listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cinema: Cinema) {
            binding.title.text = cinema.name
            binding.rating.text = "★ ${cinema.rating}"
            if (cinema.posterPreview != null)
                Picasso.get().load(cinema.posterPreview)
                    .placeholder(R.drawable.image_placeholder)
                    .into(binding.poster)
            else Picasso.get().load(R.drawable.image_placeholder)
                .placeholder(R.drawable.image_placeholder)
                .into(binding.poster)

            binding.root.setOnClickListener {
                listener.onItemClick(cinema.id)
            }
        }
    }

    object CinemaComparator : DiffUtil.ItemCallback<Cinema>() {
        override fun areItemsTheSame(oldItem: Cinema, newItem: Cinema): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cinema, newItem: Cinema): Boolean {
            return oldItem == newItem
        }
    }
}