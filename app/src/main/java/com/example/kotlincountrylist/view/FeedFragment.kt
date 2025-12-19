package com.example.kotlincountrylist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.kotlincountrylist.R
import com.example.kotlincountrylist.databinding.FragmentFeedBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlincountrylist.adapter.CountryAdapter
import com.example.kotlincountrylist.viewmodel.FeedViewModel

class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_feed,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        binding.btnFragmentFeedToCountry.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
            it.findNavController().navigate(action)
        }
        */

        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        viewModel.refreshData()

        binding.rvCountryList.layoutManager = LinearLayoutManager(context)
        binding.rvCountryList.adapter = countryAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.rvCountryList.visibility = View.GONE
            binding.tvCountryError.visibility = View.GONE
            binding.countryLoadingProgressBar.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()

        /*val myString = "James"
        myString.myExtension("Hetfield")
         */
    }

    private fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner) { countries ->
            countries?.let {
                binding.rvCountryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        }
        viewModel.countryError.observe(viewLifecycleOwner) { error ->
            error?.let {
                if (it) {
                    binding.tvCountryError.visibility = View.VISIBLE
                    binding.rvCountryList.visibility = View.GONE
                } else {
                    binding.tvCountryError.visibility = View.GONE
                }
            }
        }
        viewModel.countryLoading.observe(viewLifecycleOwner) { loading ->
            loading?.let {
                if (it) {
                    binding.rvCountryList.visibility = View.GONE
                    binding.tvCountryError.visibility = View.GONE
                    binding.countryLoadingProgressBar.visibility = View.VISIBLE
                } else {
                    binding.countryLoadingProgressBar.visibility = View.GONE
                }
            }
        }
    }
}