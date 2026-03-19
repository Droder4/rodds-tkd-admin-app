package com.roddstkd.admin

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.http.SslError
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.webkit.CookieManager
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var splashOverlay: LinearLayout
    private lateinit var offlineOverlay: LinearLayout

    private val adminUrl =
        "https://script.google.com/macros/s/AKfycbzwB7Ow4FizQnCNTKXSDmLRBm4TVXqcqqoYH6ekk6V3iz7Miyvhj6UTQ-m77JI36-11Xw/exec?page=admin"

    private fun dp(value: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            resources.displayMetrics
        ).toInt()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = FrameLayout(this)
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        }

        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
            max = 100
            visibility = View.VISIBLE
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(4)
            )
        }

        webView = WebView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        }

        container.addView(progressBar)
        container.addView(webView)

        splashOverlay = createSplashOverlay()
        offlineOverlay = createOfflineOverlay()

        root.addView(container)
        root.addView(splashOverlay)
        root.addView(offlineOverlay)

        setContentView(root)

        setupWebView()

        if (isOnline()) {
            webView.loadUrl(adminUrl)
        } else {
            showOffline()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (offlineOverlay.visibility == View.VISIBLE) {
                    offlineOverlay.visibility = View.GONE
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        finish()
                    }
                } else if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    finish()
                }
            }
        })
    }

    private fun createSplashOverlay(): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(0xFF111111.toInt())
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )

            val logo = ImageView(this@MainActivity).apply {
                setImageResource(R.mipmap.ic_launcher)
                layoutParams = LinearLayout.LayoutParams(dp(140), dp(140))
            }

            val title = TextView(this@MainActivity).apply {
                text = "Rodds TKD Admin"
                setTextColor(0xFFFFFFFF.toInt())
                textSize = 24f
                setPadding(0, dp(20), 0, dp(8))
            }

            val subtitle = TextView(this@MainActivity).apply {
                text = "Loading admin portal..."
                setTextColor(0xFFCCCCCC.toInt())
                textSize = 16f
            }

            val spinner = ProgressBar(this@MainActivity).apply {
                setPadding(0, dp(24), 0, 0)
            }

            addView(logo)
            addView(title)
            addView(subtitle)
            addView(spinner)
        }
    }

    private fun createOfflineOverlay(): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(0xFFF4F4F4.toInt())
            visibility = View.GONE
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )

            val title = TextView(this@MainActivity).apply {
                text = "You're offline"
                textSize = 24f
                setTextColor(0xFF111111.toInt())
                setPadding(dp(24), dp(24), dp(24), dp(8))
                gravity = Gravity.CENTER
            }

            val message = TextView(this@MainActivity).apply {
                text = "Check your internet connection and tap Retry to load the admin app."
                textSize = 16f
                setTextColor(0xFF444444.toInt())
                gravity = Gravity.CENTER
                setPadding(dp(24), 0, dp(24), dp(20))
            }

            val retry = Button(this@MainActivity).apply {
                text = "Retry"
                setOnClickListener {
                    if (isOnline()) {
                        offlineOverlay.visibility = View.GONE
                        splashOverlay.visibility = View.VISIBLE
                        webView.loadUrl(adminUrl)
                    } else {
                        Toast.makeText(this@MainActivity, "Still offline.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            addView(title)
            addView(message)
            addView(retry)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.loadsImagesAutomatically = true
        webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.builtInZoomControls = false
        webView.settings.displayZoomControls = false
        webView.settings.setSupportZoom(false)

        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.progress = newProgress
                progressBar.visibility = if (newProgress < 100) View.VISIBLE else View.GONE
            }
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
                if (splashOverlay.visibility != View.VISIBLE) {
                    splashOverlay.visibility = View.VISIBLE
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                splashOverlay.visibility = View.GONE
                offlineOverlay.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                showOffline()
                Toast.makeText(this@MainActivity, "Failed to load page.", Toast.LENGTH_SHORT).show()
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler,
                error: SslError
            ) {
                handler.cancel()
                showOffline("SSL error loading page.")
                Toast.makeText(this@MainActivity, "SSL error loading page.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showOffline(message: String = "Check your internet connection and tap Retry to load the admin app.") {
        splashOverlay.visibility = View.GONE
        progressBar.visibility = View.GONE
        offlineOverlay.visibility = View.VISIBLE
        val msgView = offlineOverlay.getChildAt(1) as TextView
        msgView.text = message
    }

    private fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
