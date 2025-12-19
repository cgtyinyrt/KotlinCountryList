package com.example.kotlincountrylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincountrylist.databinding.ItemCountryRvRowBinding
import com.example.kotlincountrylist.model.Country
import com.example.kotlincountrylist.util.getImageFromURL
import com.example.kotlincountrylist.util.placeholderProgressBar
import com.example.kotlincountrylist.view.FeedFragmentDirections

class CountryAdapter(
    val countryList: ArrayList<Country>
): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(
        val binding: ItemCountryRvRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryViewHolder {
        /*val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_country_rv_row, parent, false)
        return CountryViewHolder(view)

         */
        val binding = ItemCountryRvRowBinding.inflate(
            LayoutInflater.from(parent.context
            ),
            parent,
            false
        )
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CountryViewHolder,
        position: Int
    ) {
        holder.binding.tvCountryName.text = countryList[position].countryName
        holder.binding.tvCountryRegion.text = countryList[position].countryRegion

        holder.itemView.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            //action.countryUuid = countryList[position].uuid
            holder.itemView.findNavController().navigate(action)
        }

        holder.binding.imageView.getImageFromURL(
            countryList[position].countryFlagUrl,
            placeholderProgressBar(holder.itemView.context)
        )
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}