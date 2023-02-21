package com.example.webview

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.webview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        webView = binding.webview

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                println("Back button pressed")
                if (binding.webview.canGoBack())
                    binding.webview.goBack()
                else
                    finish()
            }
       })


        //val myHtml = "<center><img src=\"https://farm5.staticflickr.com/4760/39050191944_fc1b3c5214_m.jpg\" ><br /><br /></center> <p><center><font face=\"verdana\" size=\"5\" color=\"red\"><b>DIA MUNDIAL DE LA EFICIENCIA ENERGÉTICA</b></font></center> </p> <BR /> <p><font face=\"verdana\" size=\"3\"></font></p> <p> <br /> <p> <font face=\"verdana\"></font> </p> <p> <A HREF=\"https://www.youtube.com/watch?v=u1kqAte753U\"><center> <img src=\"https://thumbs.dreamstime.com/b/bot%C3%B3n-de-visualizaci%C3%B3n-ver-video-web-naranja-icono-botones-aislados-fondo-blanco-editable-ilustraci%C3%B3n-vectorial-177465113.jpg\" width=\"250\" height=\"76\"> </center></A></p>"

        //webView.loadData(myHtml, "text/html", "UTF-8")


        val ajustes = webView.settings
        ajustes.javaScriptEnabled=true

        binding.button.setOnClickListener {
            webView.goBack()
        }

        webView.loadUrl("https://as.com")
        webView.webViewClient=MyWebViewClient()
    }

    inner class MyWebViewClient: WebViewClient(){
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val url=request?.url
            val host=url?.host

            if (host!!.contains("as.com")){
                return false
            }else{
                val  intent = Intent(Intent.ACTION_VIEW,url)
                startActivity(intent)
                return true
            }
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.progressBar.isVisible=true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.progressBar.isVisible=false
        }
    }

       override fun onBackPressed() {
       if (webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        // Si no hay páginas en el historial o no es el botón ATRAS, que ejecute su funcionalidad normal
        return super.onKeyDown(keyCode, event)
    }

}