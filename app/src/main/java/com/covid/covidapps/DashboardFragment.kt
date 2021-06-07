package com.covid.covidapps

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.covid.covidapps.databinding.ActivityMainBinding
import com.covid.covidapps.databinding.FragmentDashboardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val binding = FragmentDashboardBinding.bind(view)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        val btmNav = activity.findViewById<BottomNavigationView>(R.id.btmNavBar)
        btmNav.isVisible = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}