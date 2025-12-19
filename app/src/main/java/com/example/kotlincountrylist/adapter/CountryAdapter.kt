package com.example.kotlincountrylist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincountrylist.databinding.ItemCountryRvRowBinding
import com.example.kotlincountrylist.model.Country
import com.example.kotlincountrylist.view.FeedFragmentDirections

class CountryAdapter(
    val countryList: ArrayList<Country>
): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), CountryClickListener {

    class CountryViewHolder(
        val binding: ItemCountryRvRowBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemCountryRvRowBinding>(
                inflater,
                com.example.kotlincountrylist.R.layout.item_country_rv_row,
                parent,
                false
            )
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CountryViewHolder,
        position: Int
    ) {
        holder.binding.country = countryList[position]
        holder.binding.listener = this

        /*holder.binding.tvCountryName.text = countryList[position].countryName
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
         */
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(v: View,uuid:Int) {
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        v.findNavController().navigate(action)
    }
}