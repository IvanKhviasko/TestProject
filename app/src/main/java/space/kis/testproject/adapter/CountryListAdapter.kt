package space.kis.testproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import space.kis.domain.model.Country
import space.kis.testproject.databinding.ItemCountryBinding

class CountryListAdapter(
    private val longItemClick: (Country) -> Unit,
    private val itemClick: (Country) -> Unit
) : ListAdapter<Country, CountryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            binding = ItemCountryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), longItemClick = longItemClick, itemClick = itemClick
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Country>() {

            override fun areItemsTheSame(oldCountry: Country, newCountry: Country): Boolean {
                return oldCountry.id == newCountry.id
            }

            override fun areContentsTheSame(oldCountry: Country, newCountry: Country): Boolean {
                return oldCountry == newCountry
            }
        }
    }
}