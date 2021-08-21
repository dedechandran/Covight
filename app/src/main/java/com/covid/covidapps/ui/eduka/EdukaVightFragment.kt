package com.covid.covidapps.ui.eduka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.covid.covidapps.R
import com.covid.covidapps.databinding.FragmentEdukaVightBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EdukaVightFragment : Fragment() {

    private lateinit var binding: FragmentEdukaVightBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_eduka_vight, container, false)
        binding = FragmentEdukaVightBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.wvEduka.loadUrl("https://aktiv.co.id/")
    }
}