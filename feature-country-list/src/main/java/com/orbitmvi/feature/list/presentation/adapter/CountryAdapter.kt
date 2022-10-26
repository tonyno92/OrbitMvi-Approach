package com.orbitmvi.feature.list.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.orbitmvi.feature.list.databinding.ItemCountryBinding
import com.orbitmvi.feature.list.domain.model.Country

internal class CountryAdapter(
    private val openCountryCallback: (Int) -> Unit
) : ListAdapter<Country, CountryAdapter.CountryViewHolder>(CountryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountryViewHolder(
            ItemCountryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(getItem(position), openCountryCallback)
    }

    class CountryViewHolder(private val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country, openCountryCallback: (Int) -> Unit) {
            binding.root.setOnClickListener {
                openCountryCallback(country.id)
            }
            binding.root.apply {
                load(country.flag)
                contentDescription = country.name
                tag = country.name
            }
        }
    }
}

internal class CountryDiffCallback : DiffUtil.ItemCallback<Country>() {

    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem == newItem
    }
}
