package com.dicoding.nodapplication.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.nodapplication.data.response.ReviewsItem
import com.dicoding.nodapplication.databinding.DetailRatingListItemBinding

class ReviewAdapter : ListAdapter<ReviewsItem, ReviewAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: DetailRatingListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ReviewsItem) {
            binding.tvNameRating.text = review.authorName
            binding.tvContentRating.text = review.text
            binding.review.text = review.rating.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DetailRatingListItemBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReviewsItem>() {
            override fun areItemsTheSame(
                oldItem: ReviewsItem,
                newItem: ReviewsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ReviewsItem,
                newItem: ReviewsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}