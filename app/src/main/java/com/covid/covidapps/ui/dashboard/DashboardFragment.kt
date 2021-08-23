package com.covid.covidapps.ui.dashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.covid.covidapps.R
import com.covid.covidapps.Result
import com.covid.covidapps.databinding.FragmentDashboardBinding
import com.covid.covidapps.ui.details.DetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    @ExperimentalCoroutinesApi
    private val vm by viewModels<DashboardViewModel>()

    private lateinit var binding: FragmentDashboardBinding
    private val navController by lazy {
        activity?.findNavController(R.id.navHostFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        binding = FragmentDashboardBinding.bind(view)
        binding.rvPatientSummary.setOrientation(orientation = LinearLayoutManager.HORIZONTAL)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCallAdmin.setOnClickListener {
            Intent(Intent.ACTION_VIEW).run {
                data = Uri.parse("https://wa.link/nzpcm0")
                startActivity(this)
            }
        }
        binding.btnGuide.setOnClickListener {
            navController?.navigate(R.id.action_homeFragment_to_instructionFragment)
        }
        binding.rvPatientSummary.setOnItemClickListener {
            val args = bundleOf(DetailsFragment.STATUS_EXTRA to it)
            navController?.navigate(R.id.action_homeFragment_to_detailsFragment, args)
        }
        lifecycleScope.launchWhenResumed {
            vm.state.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.rvPatientSummary.setItems(result.data.orEmpty())
                    }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}