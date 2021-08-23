package com.covid.covidapps.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.covid.covidapps.R
import com.covid.covidapps.Result
import com.covid.covidapps.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private val vm by viewModels<AccountViewModel>()
    private lateinit var binding: FragmentAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        binding = FragmentAccountBinding.bind(view)
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            vm.uiState.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        with(result.data) {
                            if (this?.userImage == "-") {
                                binding.ivAccount.setImageResource(R.drawable.ic_baseline_account_circle_56)
                            } else {
                                Glide.with(requireContext())
                                    .load(this?.userImage)
                                    .fallback(R.drawable.ic_baseline_account_circle_56)
                                    .into(binding.ivAccount)
                            }
                            binding.tvAccountName.text = this?.userName
                            binding.tvAccountMobileNo.text = this?.userMobileNo
                            binding.tvAccountEmail.text = this?.userEmail
                        }

                    }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}