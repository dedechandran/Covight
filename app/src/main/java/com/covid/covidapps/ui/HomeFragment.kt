package com.covid.covidapps.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.covid.covidapps.R
import com.covid.covidapps.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)
        binding.btmNavBar.background = null
        showBottomBarAndHideUpArrow()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController =
            (childFragmentManager.findFragmentById(R.id.homeNavHostFragment) as NavHostFragment).navController
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.instructionFragment) {
                hideBottomBarAndDisplayUpArrow()
            } else {
                showBottomBarAndHideUpArrow()
            }
        }
        binding.btmNavBar.setupWithNavController(navController)
    }

    private fun hideBottomBarAndDisplayUpArrow() {
        binding.btmAppBar.visibility = View.GONE
        binding.ivArrowBack.visibility = View.VISIBLE
        binding.btnCaseHandling.visibility = View.GONE
    }

    private fun showBottomBarAndHideUpArrow() {
        binding.btmAppBar.visibility = View.VISIBLE
        binding.ivArrowBack.visibility = View.GONE
        binding.btnCaseHandling.visibility = View.VISIBLE
        onUpArrowPressed()
    }

    private fun onUpArrowPressed() {
        binding.ivArrowBack.setOnClickListener {
            navController.popBackStack()
        }
    }
}