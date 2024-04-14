package ru.sevagrbnv.cinemaapp.presentation.cinemaDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.sevagrbnv.cinemaapp.R
import ru.sevagrbnv.cinemaapp.databinding.ActorListItemBinding
import ru.sevagrbnv.cinemaapp.domain.models.Actor

class ActorAdapter :
    PagingDataAdapter<Actor, ActorAdapter.ActorViewHolder>(ActorComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val binding =
            ActorListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    class ActorViewHolder(
        private val binding: ActorListItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(actor: Actor) {
            Picasso.get()
                .load(actor.photo)
                .placeholder(R.drawable.image_placeholder)
                .into(binding.photo)
            binding.name.text = actor.name
        }
    }

    object ActorComparator : DiffUtil.ItemCallback<Actor>() {
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem == newItem
        }
    }
}