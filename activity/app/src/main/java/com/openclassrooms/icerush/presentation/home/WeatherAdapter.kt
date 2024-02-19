package com.openclassrooms.icerush.presentation.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.icerush.databinding.ItemWeatherBinding
import com.openclassrooms.icerush.domain.model.SnowReportModel
import java.text.SimpleDateFormat
import java.util.Locale


class WeatherAdapter :
    ListAdapter<SnowReportModel, WeatherAdapter.WeatherViewHolder>(DiffCallback) {

    class WeatherViewHolder(
        private val binding: ItemWeatherBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormatter = SimpleDateFormat("dd/MM - HH:mm", Locale.getDefault())

        fun bind(observation: SnowReportModel) {
            val formattedDate: String = dateFormatter.format(observation.date.time)
            binding.textViewDateTime.text = formattedDate
            binding.textViewStargazing.text =
                if (observation.isRaining && observation.temperatureCelsius > 1) "🛑" else "️❄️"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemView =
            ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<SnowReportModel>() {
            override fun areItemsTheSame(
                oldItem: SnowReportModel,
                newItem: SnowReportModel
            ): Boolean {
                return oldItem.date == newItem.date
            }


            override fun areContentsTheSame(
                oldItem: SnowReportModel,
                newItem: SnowReportModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}




