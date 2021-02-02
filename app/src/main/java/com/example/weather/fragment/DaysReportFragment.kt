package com.example.weather.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weather.R
import com.example.weather.adapter.WeatherRecycleAdapter
import com.example.weather.databinding.FragmentDaysReportBinding
import com.example.weather.model.Main
import com.example.weather.model.Weather
import org.json.JSONException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DaysReportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DaysReportFragment : Fragment() {
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

    lateinit var binding: FragmentDaysReportBinding
    lateinit var mWeatherReportRecycle: RecyclerView
    lateinit var weatherreportAdapter: WeatherRecycleAdapter
    lateinit var date: String
    lateinit var requestQueue: RequestQueue
    lateinit var main: ArrayList<Weather>
    lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        DayReport()
    }

    private fun DayReport() {
        main = ArrayList<Weather>()
        mWeatherReportRecycle = view!!.findViewById(R.id.mWeatherReportRecycle)
        requestQueue = Volley.newRequestQueue(context)
        binding.mWeatherReportRecycle.layoutManager = LinearLayoutManager(context)

        getData()
    }

    private fun getData() {
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Data loading")
        progressDialog.setMessage("please wait")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val url =
            "http://api.openweathermap.org/data/2.5/forecast?q=bareilly&cnt=7&appid=7a5798c2e91eab10243d498dbaae7e85"
        val request = JsonObjectRequest(
            Request.Method.POST, url, null, { response ->
                progressDialog.dismiss()
                try {
                    val jsonArray = response.getJSONArray("list")
                    for (i in 0 until jsonArray.length()) {

                        val jsonObject = jsonArray.getJSONObject(i)
                        date = jsonObject.getString("dt")
                        val json_data = jsonObject.getJSONObject("main")
                        val main_data = Weather()
                        val minTemp = json_data.getDouble("temp_min")
                        val maxTemp = json_data.getDouble("temp_max")
                        main_data.main.tempMin = minTemp
                        main_data.main.tempMax = maxTemp
                        main.add(main_data)
                    }

                    weatherreportAdapter = WeatherRecycleAdapter(main, context!!)
                    mWeatherReportRecycle.adapter = weatherreportAdapter
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
        request.retryPolicy = DefaultRetryPolicy(
            20000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        request.setShouldCache(false)
        requestQueue.add(request)
    }
}