package com.dicoding.nodapplication.view.home


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.nodapplication.R
import com.dicoding.nodapplication.data.adapter.HomeAdapter
import com.dicoding.nodapplication.data.response.ListDestinationResponseItem
import com.dicoding.nodapplication.databinding.FragmentHomeBinding
import com.dicoding.nodapplication.result.Result
import com.dicoding.nodapplication.utils.showLoading
import com.dicoding.nodapplication.utils.showToast
import com.dicoding.nodapplication.view.ViewModelFactory


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<DestinationViewModel> {
        ViewModelFactory.getInstance(
            requireContext()
        )
    }

    //for logic dropdown
    private var isMenuClick = true

    //for permission
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getListNearby()
                showToast(requireContext(), "Permission request granted")
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        dropDown1Click()

        dropDown2Click()

        dropDown3Click()

        clickCategory1()

        clickCategory2()

        clickCategory3()

        clickCategory4()

        clickCategory5()

        getListNearby()

        setupRecyclerView()

        observeViewModel()

        //make click for dropdown menu
        binding.run {
            ivBurger.setOnClickListener {
                menuClick()
            }

            menuBtn1.setOnClickListener {
                viewModel.previousLocation = null
                viewModel.dropDown2Click(false)
                viewModel.dropDown3Click(false)
                viewModel.dropDown1Click(true)
                viewModel.clickCategory1(false)
                viewModel.clickCategory2(false)
                viewModel.clickCategory3(false)
                viewModel.clickCategory4(false)
                viewModel.clickCategory5(false)
                getListNearby()
            }

            menuBtn2.setOnClickListener {
                viewModel.previousLocation = null
                viewModel.dropDown2Click(true)
                viewModel.dropDown1Click(false)
                viewModel.dropDown3Click(false)
                viewModel.clickCategory1(false)
                viewModel.clickCategory2(false)
                viewModel.clickCategory3(false)
                viewModel.clickCategory4(false)
                viewModel.clickCategory5(false)
                getListReview()
            }

            menuBtn3.setOnClickListener {
                viewModel.dropDown3Click(true)
                viewModel.dropDown1Click(false)
                viewModel.dropDown2Click(false)
            }

            category1.setOnClickListener {
                viewModel.previousLocation = null

                viewModel.clickCategory1(true)
                viewModel.clickCategory2(false)
                viewModel.clickCategory3(false)
                viewModel.clickCategory4(false)
                viewModel.clickCategory5(false)

                getListCategory("Air")

            }
            category2.setOnClickListener {
                viewModel.previousLocation = null
                viewModel.clickCategory2(true)
                viewModel.clickCategory1(false)
                viewModel.clickCategory3(false)
                viewModel.clickCategory4(false)
                viewModel.clickCategory5(false)

                getListCategory("Taman")

            }
            category3.setOnClickListener {
                viewModel.previousLocation = null
                viewModel.clickCategory3(true)
                viewModel.clickCategory2(false)
                viewModel.clickCategory1(false)
                viewModel.clickCategory4(false)
                viewModel.clickCategory5(false)

                getListCategory("Monumen")

            }
            category4.setOnClickListener {
                viewModel.previousLocation = null
                viewModel.clickCategory4(true)
                viewModel.clickCategory2(false)
                viewModel.clickCategory3(false)
                viewModel.clickCategory1(false)
                viewModel.clickCategory5(false)

                getListCategory("Bukit")

            }
            category5.setOnClickListener {
                viewModel.previousLocation = null
                viewModel.clickCategory5(true)
                viewModel.clickCategory2(false)
                viewModel.clickCategory3(false)
                viewModel.clickCategory4(false)
                viewModel.clickCategory1(false)

                getListCategory("Religi")

            }

        }

        binding.etSearch.setOnEditorActionListener { textSearch, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                val search = textSearch.text.toString()
                showToast(requireContext(), "You're searching $search")
            }
            false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getListNearby() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.fetchCurrentLocation(requireContext())
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    private fun getListCategory(category: String) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.fetchCurrentCategoryLocation(requireContext(), category)
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    private fun getListReview() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.fetchCurrentReviewLocation(requireContext())
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }


    private fun observeViewModel() {
        viewModel.listDestination.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoading(true, binding.progressIndicator)

                is Result.Success -> {
                    showLoading(false, binding.progressIndicator)
                    setListDestination(result.data)
                }

                is Result.Error -> {
                    showLoading(false, binding.progressIndicator)
                    showToast(requireContext(), result.error)
                }
            }
        }

        viewModel.listDestinationReview.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoading(true, binding.progressIndicator)

                is Result.Success -> {
                    showLoading(false, binding.progressIndicator)
                    setListDestination(result.data)
                }

                is Result.Error -> {
                    showLoading(false, binding.progressIndicator)
                    showToast(requireContext(), result.error)
                }
            }
        }

        viewModel.listDestinationCategory.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoading(true, binding.progressIndicator)

                is Result.Success -> {
                    showLoading(false, binding.progressIndicator)
                    setListDestination(result.data)
                }

                is Result.Error -> {
                    showLoading(false, binding.progressIndicator)
                    showToast(requireContext(), result.error)
                }
            }
        }
    }

    private fun setListDestination(destination: List<ListDestinationResponseItem>) {
        val adapter = HomeAdapter()
        adapter.submitList(destination)
        binding.recyclerView.adapter = adapter
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun menuClick() {
        if (isMenuClick) {
            binding.ivBurger.setImageResource(R.drawable.baseline_close_24)
            binding.cvMenu.visibility = View.VISIBLE
            binding.cvTitle.visibility = View.INVISIBLE
            isMenuClick = false
        } else {
            binding.ivBurger.setImageResource(R.drawable.burger)
            binding.cvMenu.visibility = View.GONE
            binding.cvTitle.visibility = View.VISIBLE

            isMenuClick = true
        }
    }

    private fun dropDown1Click() {
        viewModel.isDropBtn1Click.observe(viewLifecycleOwner) {
            if (it) {
                binding.run {
                    menuBtn1.setBackgroundResource(R.drawable.bordered_text_background_click)
                }
                binding.tvName.text = binding.menuBtn1.text

            } else {
                binding.run {
                    menuBtn1.setBackgroundResource(R.drawable.bordered_text_background)
                }
            }
        }
    }

    private fun dropDown2Click() {
        viewModel.isDropBtn2Click.observe(viewLifecycleOwner) {
            if (it) {
                binding.run {
                    menuBtn2.setBackgroundResource(R.drawable.bordered_text_background_click)
                }
                binding.tvName.text = binding.menuBtn2.text

            } else {
                binding.run {
                    menuBtn2.setBackgroundResource(R.drawable.bordered_text_background)
                }
            }
        }
    }

    private fun dropDown3Click() {
        viewModel.isDropBtn3Click.observe(viewLifecycleOwner) {
            if (it) {
                binding.run {
                    menuBtn3.setBackgroundResource(R.drawable.bordered_text_background_click)
                    dropdownCategory.setImageResource(R.drawable.baseline_arrow_drop_up_24)
                    cvCategory.visibility = View.VISIBLE
                    binding.tvName.text = binding.menuBtn3.text
                }
            } else {
                binding.run {
                    menuBtn3.setBackgroundResource(R.drawable.bordered_text_background)
                    dropdownCategory.setImageResource(R.drawable.baseline_arrow_drop_down_24)
                    cvCategory.visibility = View.GONE

                }
            }
        }
    }


    private fun clickCategory1() {
        viewModel.isCategory1.observe(viewLifecycleOwner) {
            if (it) {
                binding.run {
                    category1.setColorFilter(
                        ContextCompat.getColor(requireContext(), R.color.green),
                        PorterDuff.Mode.SRC_IN
                    )
                }

            } else {
                binding.category1.clearColorFilter()
            }
        }
    }

    private fun clickCategory2() {
        viewModel.isCategory2.observe(viewLifecycleOwner) {
            if (it) {
                binding.run {
                    category2.setColorFilter(
                        ContextCompat.getColor(requireContext(), R.color.green),
                        PorterDuff.Mode.SRC_IN
                    )
                }

            } else {
                binding.category2.clearColorFilter()
            }
        }
    }

    private fun clickCategory3() {
        viewModel.isCategory3.observe(viewLifecycleOwner) {
            if (it) {
                binding.run {
                    category3.setColorFilter(
                        ContextCompat.getColor(requireContext(), R.color.green),
                        PorterDuff.Mode.SRC_IN
                    )
                }
            } else {
                binding.category3.clearColorFilter()
            }
        }
    }

    private fun clickCategory4() {
        viewModel.isCategory4.observe(viewLifecycleOwner) {
            if (it) {
                binding.run {
                    category4.setColorFilter(
                        ContextCompat.getColor(requireContext(), R.color.green),
                        PorterDuff.Mode.SRC_IN
                    )
                }
            } else {
                binding.category4.clearColorFilter()
            }
        }
    }

    private fun clickCategory5() {
        viewModel.isCategory5.observe(viewLifecycleOwner) {
            if (it) {
                binding.run {
                    category5.setColorFilter(
                        ContextCompat.getColor(requireContext(), R.color.green),
                        PorterDuff.Mode.SRC_IN
                    )
                }

            } else {
                binding.category5.clearColorFilter()
            }
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
    }
}