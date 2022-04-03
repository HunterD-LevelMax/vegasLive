package com.liveapp.casvegas

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.liveapp.casvegas.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonStart.visibility = View.GONE


        if (checkInternet(this)) {
            requestUrl(LINK)
        } else {
            init()
        }
    }

    private fun init() {
        binding.apply {
            buttonStart.visibility = View.VISIBLE
            buttonStart.setOnClickListener {
                replaceActivity(SpinActivity())
            }
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    private fun requestUrl(url: String) {
        thread {
            val request = Request.Builder()
                .url(url)
                .addHeader("User-Agent", System.getProperty("http.agent").toString())
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        binding.buttonStart.visibility = View.VISIBLE
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val json = response.body()?.string()
                    if (json != "" && json != null) {
                        //Log.d("json", json)
                        runOnUiThread {
                            replaceActivity(WebActivity(), json)
                        }
                    } else {
                        runOnUiThread {
                            //Log.d("json", "$json - empty url")
                            init()
                        }
                    }
                }
            })
        }
    }

    private fun checkInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
        return false
    }
}