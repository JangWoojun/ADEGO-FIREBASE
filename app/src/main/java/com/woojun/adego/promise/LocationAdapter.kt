package com.woojun.adego.promise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.woojun.adego.dataClass.Location
import com.woojun.adego.databinding.LocationItemBinding

class LocationAdapter(private val locationList: MutableList<Location>): RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = LocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding).also { handler->
            binding.apply {

            }
        }
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(locationList[position])
    }

    class LocationViewHolder(private val binding: LocationItemBinding):
        ViewHolder(binding.root) {
        fun bind(location: Location) {
            binding.locationName.text = location.location
            binding.locationDetailsName.text = location.locationDetails
        }
    }

}