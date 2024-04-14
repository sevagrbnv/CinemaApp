package ru.sevagrbnv.cinemaapp.presentation.cinemaDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.sevagrbnv.cinemaapp.databinding.ReviewItemListBinding
import ru.sevagrbnv.cinemaapp.domain.models.Review

class ReviewAdapter() :
    PagingDataAdapter<Review, ReviewAdapter.ReviewViewHolder>(ReviewComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding =
            ReviewItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    class ReviewViewHolder(
        private val binding: ReviewItemListBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.author.text = review.author
            binding.title.text = review.title
            binding.review.text = review.review
        }
    }

    object ReviewComparator : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem == newItem
        }
    }
}