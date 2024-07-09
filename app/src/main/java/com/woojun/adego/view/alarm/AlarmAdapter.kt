package com.woojun.adego.view.alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.woojun.adego.dataClass.User
import com.woojun.adego.databinding.AlarmItemBinding

class AlarmAdapter(private val alarmList: MutableList<User>): RecyclerView.Adapter<AlarmAdapter.HiddenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiddenViewHolder {
        val binding = AlarmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HiddenViewHolder(binding).also { handler->
            binding.apply {

            }
        }
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    override fun onBindViewHolder(holder: HiddenViewHolder, position: Int) {
        holder.bind(alarmList[position])
    }

    class HiddenViewHolder(private val binding: AlarmItemBinding):
        ViewHolder(binding.root) {
        fun bind(user: User) {
            if (binding.root.context != null) {
                Glide.with(binding.root.context)
                    .load(user.profileImage)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.imageView)
            }
            binding.nicknameText.text = user.nickname
        }
    }

}