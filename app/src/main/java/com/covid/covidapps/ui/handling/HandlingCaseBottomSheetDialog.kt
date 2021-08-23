package com.covid.covidapps.ui.handling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.covid.covidapps.R
import com.covid.covidapps.databinding.HandlingCaseLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HandlingCaseBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: HandlingCaseLayoutBinding
    private val handlingCaseAdapter by lazy {
        HandlingCaseAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.handling_case_layout, container)
        binding = HandlingCaseLayoutBinding.bind(view)
        binding.rvHandlingCase.apply {
            adapter = handlingCaseAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        return view
    }

    companion object {
        fun newInstance(data: List<HandlingCaseItem>, listener: (String) -> Unit): HandlingCaseBottomSheetDialog{
            return HandlingCaseBottomSheetDialog().apply {
                handlingCaseAdapter.submitList(data)
                handlingCaseAdapter.setOnItemClickListener(listener)
            }
        }
    }
}