package com.example.weather.activity

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weather.R
import com.example.weather.fragment.DateFragment
import com.example.weather.fragment.DaysReportFragment
import com.example.weather.fragment.HomeFragment
import com.example.weather.fragment.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    lateinit var mFrame: FrameLayout
    lateinit var mTitle: TextView
    lateinit var mNavigationView: BottomNavigationView
    lateinit var name: String
    lateinit var cityname: String
    lateinit var mUserName: TextView
    lateinit var homeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mNavigationView = findViewById(R.id.mNavigationView)
        mTitle = findViewById(R.id.mTitle)
        mUserName = findViewById(R.id.mUserName)
        name = intent.getStringExtra("name")!!
        cityname = intent.getStringExtra("cityname")!!
        Toast.makeText(this, cityname, Toast.LENGTH_LONG).show()
        mTitle.text = "Home"
        mUserName.text = name
        homeFragment = HomeFragment.newInstance(cityname, "")
        loadFragment(homeFragment)
        mNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_Current -> {
                    mTitle.text = "Home"
                    title = resources.getString(R.string.current)
                    homeFragment = HomeFragment.newInstance(cityname,"")
                    loadFragment(homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_Date -> {
                    mTitle.text = "Date"
                    title = resources.getString(R.string.navigation_date)
                    loadFragment(DateFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_7daysReport -> {
                    mTitle.text = "7 Days Report"
                    title = resources.getString(R.string.report)
                    loadFragment(DaysReportFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_settings -> {
                    mTitle.text = "Settings"
                    title = resources.getString(R.string.settings)
                    loadFragment(SettingsFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mFrame, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}