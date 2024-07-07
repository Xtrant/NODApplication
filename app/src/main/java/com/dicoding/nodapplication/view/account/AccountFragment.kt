package com.dicoding.nodapplication.view.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.nodapplication.databinding.FragmentAccountBinding
import com.dicoding.nodapplication.result.Result
import com.dicoding.nodapplication.utils.showLoading
import com.dicoding.nodapplication.utils.showToast
import com.dicoding.nodapplication.view.ViewModelFactory
import com.dicoding.nodapplication.view.auth.AuthViewModel
import com.dicoding.nodapplication.view.auth.SplashScreen


class Account : Fragment() {

    private var _binding: FragmentAccountBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<AuthViewModel> {
        ViewModelFactory.getInstance(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val view = binding.root

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getEmail().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.contentEmail.text = it
            }
        }

        observeViewModel()

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun observeViewModel() {
        viewModel.resultMessage.observe(viewLifecycleOwner) {result->
            if (result != null)
                when (result) {
                    is Result.Loading -> {
                        showLoading(true, binding.progressIndicator)
                    }

                    is Result.Success -> {
                        showLoading(false, binding.progressIndicator)
                        result.data.message?.let { showToast(requireContext(), it) }
                        viewModel.clearSession()
                        startActivity(Intent(requireContext(), SplashScreen::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        })

                    }

                    is Result.Error -> {
                        showLoading(false, binding.progressIndicator)
                        showToast(requireContext(), result.error)

                    }
                }
        }
    }
}