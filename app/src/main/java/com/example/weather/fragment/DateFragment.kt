package com.example.weather.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weather.R
import com.example.weather.databinding.FragmentDateBinding
import org.json.JSONException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentDateBinding
    lateinit var requestQueue: RequestQueue
    lateinit var cityname: String
    lateinit var city: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setup()
    }

    private fun setup() {
        val cities = resources.getStringArray(R.array.City)
        val adapter = ArrayAdapter(
            context!!,
            android.R.layout.simple_spinner_dropdown_item, cities
        )
        binding.mCitySpinner.adapter = adapter

        binding.mCitySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                city = cities.get(position)
                initialize()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun initialize() {
        requestQueue = Volley.newRequestQueue(context)
        val url =
            "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=7a5798c2e91eab10243d498dbaae7e85"
        val request = JsonObjectRequest(
            Request.Method.POST, url, null, { response ->
                try {
                    cityname = response.getString("name")
                    binding.mcity.text = cityname
                    val jsonObject = response.getJSONObject("main")
                    val temp = jsonObject.getDouble("temp")
                    val temp_min = jsonObject.getDouble("temp_min")
                    val temp_max = jsonObject.getDouble("temp_max")
                    val tem = ((temp - 273.15))
                    binding.mTemp.text = String.format("%.0f", tem) + " \u2103"
                    val mintem = ((temp_min - 273.15))
                    val maxtem = ((temp_max - 273.15))
                    binding.mMinTemp.text = String.format("%.0f", mintem) + " \u2103"
                    binding.mMaxTemp.text = String.format("%.0f", maxtem) + " \u2103"
                    val jsonwind = response.getJSONObject("wind")
                    val windspeed = jsonwind.getString("speed")
                    binding.mWindSpeed.text = windspeed
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
                Log.d("eerr", error.toString())
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            })
        request.setRetryPolicy(
            DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        request.setShouldCache(false)
        requestQueue.add(request)
    }
}