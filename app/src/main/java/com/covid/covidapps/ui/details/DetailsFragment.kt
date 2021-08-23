package com.covid.covidapps.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.covid.covidapps.R
import com.covid.covidapps.Result
import com.covid.covidapps.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val vm by viewModels<DetailsViewModel>()
    private lateinit var binding: FragmentDetailsBinding
    private var status: String? = null
    private val navController by lazy {
        activity?.findNavController(R.id.navHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        binding = FragmentDetailsBinding.bind(view)
        status = arguments?.getString(STATUS_EXTRA)
        binding.rvPatientDetails.setOrientation(orientation = LinearLayoutManager.VERTICAL)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivArrowBack.setOnClickListener {
            navController?.popBackStack()
        }
        status?.run {
            vm.initialize(this)
        }
        lifecycleScope.launchWhenResumed {
            vm.uiState.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.rvPatientDetails.setItems(result.data.orEmpty())
                    }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object {
        const val STATUS_EXTRA = "status"
    }
}