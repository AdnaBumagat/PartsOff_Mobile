package com.bumagat.partsoff_webview

import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.web)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        setupWebView()

        swipeRefreshLayout.setOnRefreshListener {
            webView.reload()
        }

        webView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            swipeRefreshLayout.isEnabled = scrollY == 0
        }
        onBackPressedDispatcher.addCallback(this) {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                finish()
            }
        }
    }

    private fun setupWebView() {
        webView.loadUrl("http://64.23.185.162")
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {

                swipeRefreshLayout.isRefreshing = false
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                swipeRefreshLayout.isRefreshing = false
            }
        }
        webView.settings.setSupportZoom(true)
    }

}