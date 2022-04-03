package com.liveapp.casvegas

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.liveapp.casvegas.databinding.ActivityWebBinding


class WebActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebBinding
    private lateinit var URL: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        URL = intent.getStringExtra("url").toString() //полученный адрес из запроса

        CookieManager.getInstance().acceptCookie()
        CookieManager.getInstance().flush() // сохранение cookies
        setupWebView(URL)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(url: String) {
        binding.webWidget.webViewClient = WebViewClient()
        binding.webWidget.loadUrl(url)
        binding.webWidget.apply {
            settings.apply {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                cacheMode
                allowContentAccess = true
                userAgentString
                setSupportMultipleWindows(true)
                domStorageEnabled = true
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                settings.safeBrowsingEnabled = true
            }
        }
    }

    override fun onBackPressed() {
        if (binding.webWidget.canGoBack()) {
            binding.webWidget.goBack()
            CookieManager.getInstance().flush()
        } else {
            CookieManager.getInstance().flush()
        }
    }

    override fun onStop() {
        CookieManager.getInstance().flush()
        super.onStop()
    }


    override fun onResume() {
        super.onResume()
        CookieManager.getInstance().flush()
    }

}