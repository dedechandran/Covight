package com.covid.covidapps.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.covid.covidapps.R
import com.covid.covidapps.databinding.FragmentInstructionBinding
import com.covid.covidapps.ui.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstructionFragment : Fragment() {

    private lateinit var binding: FragmentInstructionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        (parentFragment as HomeFragment).apply {
//            hideBottomBarAndDisplayUpArrow()
//            onUpArrowPressed()
//        }
        val view = inflater.inflate(R.layout.fragment_instruction, container, false)
        binding = FragmentInstructionBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvInstruction.text = "1. Letakan alat tepat di bagian perut bawah Dada Pasien \n2. Lingkarkan dan kencangkan belt"
    }
}