package com.dicoding.nodapplication.data.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.nodapplication.data.parcelabe.Destination
import com.dicoding.nodapplication.data.response.ListDestinationResponseItem
import com.dicoding.nodapplication.databinding.DestinationListItemBinding
import com.dicoding.nodapplication.view.home.DetailActivity

class HomeAdapter :
    ListAdapter<ListDestinationResponseItem, HomeAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: DestinationListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(destination: ListDestinationResponseItem) {
            binding.tvName.text = destination.namaWisata
            binding.tvRating.text = destination.rating.toString()
            binding.tvDistance.text = destination.userDistance
            binding.tvTotalReview.text = destination.reviews.toString()

            itemView.setOnClickListener {
                val dataDestination = Destination(
                    destination.reviews.toString(),
                    destination.namaWisata.toString(),
                    destination.jenisWisata.toString(),
                    destination.rating.toString(),
                    destination.userDistance.toString(),
                    destination.kabupatenKota.toString()
                )
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.DESTINATION, dataDestination)
                intent.putExtra(DetailActivity.NAME, destination.namaWisata)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.ivIcon, "img"),
                        Pair(binding.tvName, "name"),
                        Pair(binding.tvRating, "rating"),
                        Pair(binding.tvTotalReview, "total_review"),
                        Pair(binding.tvDistance, "distance"),
                        Pair(binding.ivStar, "star"),
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DestinationListItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val destination = getItem(position)
        holder.bind(destination)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListDestinationResponseItem>() {
            override fun areItemsTheSame(
                oldItem: ListDestinationResponseItem,
                newItem: ListDestinationResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListDestinationResponseItem,
                newItem: ListDestinationResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}






