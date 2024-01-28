package vn.trunglt.demowebviewinteraction

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebMessage
import android.webkit.WebView
import android.webkit.WebViewClient
import vn.trunglt.demowebviewinteraction.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var client: VinacapitalClient
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        client = VinacapitalClient(binding.wv)
        binding.wv.apply {
            webViewClient = client
            settings.javaScriptEnabled = true
            loadUrl("https://badc-118-69-157-71.ngrok-free.app/")
            addJavascriptInterface(MyJsInterface(), "AndroidInterface")
        }
    }
}

class VinacapitalClient(private val webView: WebView): WebViewClient() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        println("Loading page...")
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        println("Done!")
        val jsCode = "fillData('Hello world')"
        webView.evaluateJavascript(jsCode, null)
//        val moreJsCode = "fillData('Hehe')"
        val moreJsCode = "function showAlertHello('Hello world') { alert('This function is written by Android!'); }"
        webView.loadUrl("javascript:${moreJsCode}")
        val webMessage = WebMessage("")
        webView.postWebMessage()
    }
}

class MyJsInterface() {
    @JavascriptInterface
    fun getSearchValue(data: String) {
        println(data)
    }
}