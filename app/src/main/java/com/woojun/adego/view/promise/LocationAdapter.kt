package com.woojun.adego.view.promise

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.woojun.adego.database.AppPreferences
import com.woojun.adego.dataClass.Location
import com.woojun.adego.dataClass.PromiseInfo
import com.woojun.adego.databinding.LocationItemBinding


class LocationAdapter(private val locationList: MutableList<Location>, private val promiseInfo: PromiseInfo): RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = LocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding).also { handler->
            binding.locationBox.setOnClickListener {
                AppPreferences.promiseName = promiseInfo.name
                AppPreferences.promiseDate = promiseInfo.date
                AppPreferences.promiseTime = promiseInfo.time
                AppPreferences.promiseLocation = locationList[handler.position].location
                AppPreferences.promiseLongitude = locationList[handler.position].longitude
                AppPreferences.promiseLatitude = locationList[handler.position].latitude

                binding.root.context.startActivity(Intent(binding.root.context, PromiseFinishActivity::class.java))
                (binding.root.context as Activity?)?.finishAffinity()
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
             if (location.locationDetailsMain != "" ) {
                binding.locationDetailsName.text = location.locationDetailsMain
            } else if (location.locationDetailsSub != "") {
                 binding.locationDetailsName.text = location.locationDetailsSub
            } else {
                binding.locationDetailsName.visibility = View.GONE
            }
        }
    }

}