package com.covid.covidapps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.covid.covidapps.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val vm by viewModels<SplashViewModel>()
    private val navController by lazy {
        findNavController()
    }
    private lateinit var binding: FragmentSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        binding = FragmentSplashBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            vm.loginState.collect { result ->
                when(result){
                    is Result.Success -> {
                        if (result.data == true) {
                            navController.navigate(R.id.action_splashFragment_to_menuDashboard)
                        } else {
                            navController.navigate(R.id.action_splashFragment_to_loginFragment)
                        }
                    }
                    is Result.Init -> {

                    }
                    is Result.Loading -> {

                    }
                }

            }
        }
        binding.btnStartNow.setOnClickListener {
            vm.startNow()
        }

    }
}