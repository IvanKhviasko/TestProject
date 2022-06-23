package space.kis.testproject.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import space.kis.domain.model.Country
import space.kis.testproject.databinding.ItemCountryBinding

class CountryViewHolder(
    private val binding: ItemCountryBinding,
    private val longItemClick: (Country) -> Unit,
    private val itemClick: (Country) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Country) {
        binding.name.text = item.name
        binding.CountryImage.load(item.flag)

        binding.root.setOnClickListener {
            itemClick(item)
        }

        binding.root.setOnLongClickListener {
            longItemClick(item)
            true
        }
    }
}