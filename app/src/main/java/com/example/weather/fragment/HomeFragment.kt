package com.example.weather.fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weather.R
import com.example.weather.databinding.FragmentHomeBinding
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var cityname: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cityname = it.getString("cityname")
            param2 = it.getString(param2)
        }
    }

    lateinit var binding: FragmentHomeBinding
    lateinit var name: String
    lateinit var city: String
    lateinit var currentTime: String
    lateinit var main: String
    lateinit var requestQueue: RequestQueue
    lateinit var mtemp_min: TextView
    lateinit var mtemp_max: TextView
    lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        home()
    }

    @SuppressLint("SimpleDateFormat")
    private fun home() {
        mtemp_min = view!!.findViewById(R.id.mtemp_min)
        mtemp_max = view!!.findViewById(R.id.mtemp_max)
        requestQueue = Volley.newRequestQueue(context)
        val sdf = SimpleDateFormat("EEE")
        val d = Date()
        val dayOfTheWeek = sdf.format(d)

        currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        binding.mTime.text = currentTime
        binding.mDay.text = dayOfTheWeek

        network()
    }

    private fun network() {
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences("FAR", Context.MODE_PRIVATE)
        val unit = sharedPreferences.getBoolean("far", true)
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Data loading")
        progressDialog.setMessage("please wait")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val url =
            "http://api.openweathermap.org/data/2.5/weather?q=" + cityname + "&appid=7a5798c2e91eab10243d498dbaae7e85"
        val request = JsonObjectRequest(Request.Method.POST, url, null, { response ->
            progressDialog.dismiss()
            try {
                city = response.getString("name")
                binding.mCityName.text = city
                val jsonObject = response.getJSONObject("coord")
                val lon = jsonObject.getString("lon")
                val lat = jsonObject.getString("lat")
                Log.d("lon", jsonObject.getString("lon"))
                Log.d("lat", jsonObject.getString("lat"))
                val jsonArray = response.getJSONArray("weather")
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    main = jsonObject.getString("main")
                    binding.mSkyCondition.text = main
                }

                val wind_data = response.getJSONObject("wind")
                val wind_speed = wind_data.getString("speed")
                binding.mWindSpeed.text = "" + wind_speed

                val json_data = response.getJSONObject("main")
                val feels = json_data.getDouble("feels_like")
                val feels_like = ((feels - 273.15))
                binding.mFeelsLike.text = String.format("%.0f", feels_like) + " \u2103"
                binding.mHumidity.text = json_data.getString("humidity")
                val temp = json_data.getDouble("temp")
                if (unit == true) {
                    val tem = ((temp - 273.15))
                    binding.mTemperature.text = String.format("%.0f", tem) + " \u2103"
                } else {
                    val tem = ((temp - 273.15) * 9 / 5) + 32
                    binding.mTemperature.text = String.format("%.0f", tem) + " \u2109"
                }


//                binding.mWindSpeed.text = jsonObject.getString("humidity")
                binding.mPressure.text = json_data.getString("pressure")
                val mintemp = json_data.getDouble("temp_min")
                val min_tem = ((mintemp - 273.15))
                val maxtemp = json_data.getDouble("temp_max")
                val max_tem = ((maxtemp - 273.15))
                mtemp_min.text = String.format("%.0f", min_tem) + " \u2103"
                mtemp_max.text = String.format("%.0f", max_tem) + " \u2103"
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        },
            { error ->
                progressDialog.dismiss()
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
        requestQueue?.add(request)

    }


    // Converts to fahrenheit
    private fun convertCelciusToFahrenheit(celsius: Float): Float {
        return celsius * 9 / 5 + 32
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(cityname: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString("cityname", cityname)
                    putString("param2", param2)
                }
            }
    }
}