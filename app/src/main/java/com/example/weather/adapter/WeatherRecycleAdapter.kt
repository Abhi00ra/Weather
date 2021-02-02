package com.example.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.model.ForecastReport
import com.example.weather.model.Main
import com.example.weather.model.Weather

public class WeatherRecycleAdapter(var forecast: ArrayList<Weather>, var context: Context) :
    RecyclerView.Adapter<WeatherRecycleAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mForecast_mintemp: TextView
        var mForecast_maxtemp: TextView
        var mForecast_date: TextView
        var mForecast_Sky: TextView

        init {
            mForecast_mintemp = itemView.findViewById(R.id.mForecast_mintemp)
            mForecast_maxtemp = itemView.findViewById(R.id.mForecast_maxtemp)
            mForecast_date = itemView.findViewById(R.id.mForecast_date)
            mForecast_Sky = itemView.findViewById(R.id.mForecast_Sky)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mForecast_date.text =""+forecast.get(position).dt

        val maxtem = ((forecast.get(position).main.tempMax - 273.15))
        holder.mForecast_maxtemp.text = String.format("%.0f", maxtem) + "\u2103"

        val mintem = ((forecast.get(position).main.tempMin - 273.15))
        holder.mForecast_mintemp.text = String.format("%.0f", mintem) + "\u2103"
    }

    override fun getItemCount(): Int {
        return forecast.size
    }
}