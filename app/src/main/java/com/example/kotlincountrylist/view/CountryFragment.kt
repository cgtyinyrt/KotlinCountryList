package com.example.kotlincountrylist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.kotlincountrylist.R
import com.example.kotlincountrylist.databinding.FragmentCountryBinding
import com.example.kotlincountrylist.viewmodel.CountryViewModel

class CountryFragment : Fragment() {

    private lateinit var binding: FragmentCountryBinding
    private lateinit var viewModel: CountryViewModel
    private var countryUuid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*val test = Country(
            "Turkey",
            "Ankara",
            "Eurasia",
            "Turkish Lira",
            "https://restcountries.com/data/tur.svg",
            "Turkish"
        )
         */
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_country,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProvider(this)[CountryViewModel::class.java]
        viewModel.getDataFromRoom(countryUuid)

        observeLiveData()

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner
        ) {
            val action = CountryFragmentDirections.actionCountryFragmentToFeedFragment()
            view.findNavController().navigate(action)
        }
    }

    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner) { country ->
            country?.let {
                binding.selectedCountry = country

//                binding.tvCountryName.text = country.countryName
//                binding.tvCountryCapital.text = country.countryCapital
//                binding.tvCountryRegion.text = country.countryRegion
//                binding.tvCountryCurrency.text = country.countryCurrency
//                binding.tvCountryLanguage.text = country.countryLanguage
//                context?.let {
//                    binding.ivCountryFlagImage.getImageFromURL(country.countryFlagUrl,
//                        placeholderProgressBar(it)
//                    )
//                }
            }
        }
    }
}