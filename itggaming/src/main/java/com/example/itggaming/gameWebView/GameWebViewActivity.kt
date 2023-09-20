package com.example.itggaming.gameWebView

import android.app.Dialog
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.itggaming.GameLanding.api.model.Games
import com.example.itggaming.R
import com.example.itggaming.util.GameConstants
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton


class GameWebViewActivity : AppCompatActivity() {

    private lateinit var appbar: AppBarLayout
    private lateinit var dataset: Games
    private lateinit var gameName: TextView
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game_web_view)
        setBackButton()
        setIntentData()
        setupWebView()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showDialog()
            }
        })

    }

    private fun setIntentData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            setData(intent.getSerializableExtra(GameConstants.GAME_DATA, Games::class.java))
        } else {
            setData(intent.getSerializableExtra(GameConstants.GAME_DATA) as Games?)
        }
    }

    private fun setupWebView() {
        webView = findViewById(R.id.gameWebView)

        webView.settings.javaScriptEnabled = true
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportZoom(false)
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.settings.mediaPlaybackRequiresUserGesture = false
        webView.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)

        dataset.gamePlayUrl?.let { webView.loadUrl(it) }
    }

    private fun setBackButton() {
        val back_btn = findViewById<ImageView>(R.id.iv_web_back_btn)
        back_btn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setData(data: Games?) {
        if (data != null) {
            dataset = data
        }
        appbar = findViewById(R.id.appBar)
        gameName = findViewById(R.id.tv_game_title)

        val isFullScreen = data?.isFullscreen
        if (isFullScreen != null) {
            if (isFullScreen.equals("true")) {
                appbar.setExpanded(false, true)
                hideSystemBars(true, true)
            } else {
                appbar.setExpanded(true, true)
                hideSystemBars(true, false)
            }
        }

        if (data != null) {
            gameName.text = data.gameName
        }

        val orientation = data?.orientation
        if (orientation != null) {
            if (orientation.equals("port")) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }
    }

    private fun hideSystemBars(isNavHidden: Boolean, isStatusHidden: Boolean) {
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        if (isNavHidden && isStatusHidden) {
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            )
        } else if (isNavHidden) {
            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
            window.statusBarColor = getColor(R.color.toolbar)
        }
    }

    fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val mDialogNo = dialog.findViewById<Button>(R.id.bt_no)
        mDialogNo.setOnClickListener {
            dialog.dismiss()
        }
        val mDialogOk = dialog.findViewById<Button>(R.id.bt_yes)
        mDialogOk.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.show()
    }
}