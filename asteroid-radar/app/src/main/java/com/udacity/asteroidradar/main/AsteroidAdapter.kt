package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.model.Asteroid
import androidx.recyclerview.widget.ListAdapter

class AsteroidAdapter : ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>(DiffCallback) {

    private var asteroids = listOf<Asteroid>()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.asteroid_item, parent, false)

        return AsteroidViewHolder(view)
    }

    override fun getItemCount(): Int {
        return asteroids.size
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = asteroids[position]
        holder.codeName.text = item.codename
        holder.approachDate.text = item.closeApproachDate

        holder.hazardImage.setImageResource(if (item.isPotentiallyHazardous)
            R.drawable.ic_status_potentially_hazardous else R.drawable.ic_status_normal)
    }

    class AsteroidViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val codeName: TextView = itemView.findViewById(R.id.code_name)
        val approachDate: TextView = itemView.findViewById(R.id.close_approach_date)
        val hazardImage: ImageView = itemView.findViewById(R.id.hazardImage)
    }
}

