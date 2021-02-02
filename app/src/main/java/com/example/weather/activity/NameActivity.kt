package com.example.weather.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.databinding.ActivityNameBinding
import com.example.weather.utils.AppUtils

class NameActivity : AppCompatActivity() {

    lateinit var binding: ActivityNameBinding
    lateinit var name: String
    lateinit var cityname: String
    lateinit var lat: String
    lateinit var lon: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNameBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.mNext.setOnClickListener { view ->
            if (valid()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("name",name)
                intent.putExtra("cityname",cityname)
                startActivity(intent)
            }
        }
    }

    private fun valid(): Boolean {
        name = binding.mName.text.toString()
        cityname = binding.mCityN.text.toString()
        if (name.isEmpty()) {
            AppUtils.toast(this, "Name should not be null")
            return false
        }
        if (cityname.isEmpty()) {
            AppUtils.toast(this, "City Name for weather details")
            return false
        }
        return true
    }
}