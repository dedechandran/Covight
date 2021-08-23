package com.covid.covidapps.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.covid.covidapps.R
import com.covid.covidapps.databinding.FragmentHomeBinding
import com.covid.covidapps.ui.details.DetailsFragment
import com.covid.covidapps.ui.handling.HandlingCaseBottomSheetDialog
import com.covid.covidapps.ui.handling.HandlingCaseItem
import com.covid.covidapps.utils.PatientStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeNavController: NavController
    private lateinit var binding: FragmentHomeBinding
    private lateinit var bottomSheetDialog: HandlingCaseBottomSheetDialog
    private val mainNavController by lazy {
        activity?.findNavController(R.id.navHostFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)
        binding.btmNavBar.background = null
        bottomSheetDialog = HandlingCaseBottomSheetDialog.newInstance(
            data = listOf(
                HandlingCaseItem(
                    id = "1",
                    action = "Penanganan Tim dan Dokter Ahli",
                    image = R.drawable.ic_kritis,
                    status = PatientStatus.KRITIS
                ),
                HandlingCaseItem(
                    id = "2",
                    action = "Pemasangan Ventilator",
                    image = R.drawable.ic_berat,
                    status = PatientStatus.BERAT
                ),
                HandlingCaseItem(
                    id = "3",
                    action = "Pemasangan Oksigen",
                    image = R.drawable.ic_sedang,
                    status = PatientStatus.SEDANG
                ),
                HandlingCaseItem(
                    id = "4",
                    action = "Test Swab",
                    image = R.drawable.ic_ringan,
                    status = PatientStatus.RINGAN
                )
            ),
            listener = {
                val args = bundleOf(DetailsFragment.STATUS_EXTRA to it)
                mainNavController?.navigate(R.id.action_homeFragment_to_detailsFragment, args)
                bottomSheetDialog.dismiss()
            }
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeNavController =
            (childFragmentManager.findFragmentById(R.id.homeNavHostFragment) as NavHostFragment).navController
        binding.btmNavBar.setupWithNavController(homeNavController)
        binding.btnCaseHandling.setOnClickListener {
            bottomSheetDialog.show(
                childFragmentManager,
                HandlingCaseBottomSheetDialog::class.simpleName
            )
        }
    }
}