package com.dicoding.nodapplication.view.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.nodapplication.data.adapter.HomeAdapter
import com.dicoding.nodapplication.data.response.ListDestinationResponseItem
import com.dicoding.nodapplication.databinding.FragmentWishlistBinding
import com.dicoding.nodapplication.view.ViewModelFactory


class WishlistFragment : Fragment() {
    private var _binding: FragmentWishlistBinding? = null
    val binding get() = _binding!!

    private val viewModel by viewModels<WishlistViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWishlistBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        setWishlist()
    }

    private fun setWishlist() {
        viewModel.getAllWishlist().observe(viewLifecycleOwner) { destination ->
            val items = arrayListOf<ListDestinationResponseItem>()
            destination.map {
                val item = ListDestinationResponseItem(
                    namaWisata = it.destinationName,
                    rating = it.destinationRating,
                    reviews = it.destinationAmountReview,
                    userDistance = it.destinationDistance,
                    kabupatenKota = it.destinationCity,
                    jenisWisata = it.destinationType
                )

                items.add(item)
            }
            if (items.size != 0) {
                val adapter = HomeAdapter()
                adapter.submitList(items)
                binding.recyclerView.adapter = adapter
                binding.tvWarning.visibility = View.GONE
            }
            else binding.tvWarning.visibility = View.VISIBLE
        }
    }


}