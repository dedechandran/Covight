package com.covid.covidapps

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.covid.covidapps.databinding.FragmentPatientBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PatientFragment : Fragment() {

    private val vm by viewModels<PatientViewModel>()
    private lateinit var binding: FragmentPatientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_patient, container, false)
        binding = FragmentPatientBinding.bind(view)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            vm.state.collect { result ->
                when (result) {
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        binding.tvFsrValue.text = ": ${result.data?.FSR}"
                        binding.tvHeartRateValue.text = ": ${result.data?.heartRate}"
                        binding.tvSpO2Value.text = ": ${result.data?.spO2}"
                        binding.tvTemperatureValue.text = ": ${result.data?.temperature}"
                    }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}