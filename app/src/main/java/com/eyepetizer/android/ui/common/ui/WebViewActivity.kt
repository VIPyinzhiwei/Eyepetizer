/*
 * Copyright (c) 2020. vipyinzhiwei <vipyinzhiwei@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eyepetizer.android.ui.common.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.*
import com.eyepetizer.android.databinding.ActivityWebViewBinding
import com.eyepetizer.android.extension.logD
import com.eyepetizer.android.extension.preCreateSession
import com.eyepetizer.android.extension.visible
import com.eyepetizer.android.ui.common.ui.vassonic.OfflinePkgSessionConnection
import com.eyepetizer.android.ui.common.ui.vassonic.SonicJavaScriptInterface
import com.eyepetizer.android.ui.common.ui.vassonic.SonicRuntimeImpl
import com.eyepetizer.android.ui.common.ui.vassonic.SonicSessionClientImpl
import com.eyepetizer.android.util.GlobalUtil
import com.tencent.sonic.sdk.*


/**
 * 展示网页共通界面。
 *
 * @author vipyinzhiwei
 * @since  2020/5/22
 */
class WebViewActivity : BaseActivity() {

    private var _binding: ActivityWebViewBinding? = null

    private val binding: ActivityWebViewBinding
        get() = _binding!!

    private var title: String = ""

    private var linkUrl: String = ""

    private var isShare: Boolean = false

    private var isTitleFixed: Boolean = false

    private var sonicSession: SonicSession? = null

    private var sonicSessionClient: SonicSessionClientImpl? = null

    private var mode: Int = MODE_DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParams()
        preloadInitVasSonic()
        _binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setupViews() {
        super.setupViews()
        initTitleBar()
        initWebView()
        if (sonicSessionClient != null) {
            sonicSessionClient?.bindWebView(binding.webView)
            sonicSessionClient?.clientReady()
        } else {
            binding.webView.loadUrl(linkUrl)
        }
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        binding.webView.destroy()
        sonicSession?.destroy()
        sonicSession = null
        _binding = null
        super.onDestroy()
    }

    private fun initParams() {
        title = intent.getStringExtra(TITLE) ?: GlobalUtil.appName
        linkUrl = intent.getStringExtra(LINK_URL) ?: DEFAULT_URL
        isShare = intent.getBooleanExtra(IS_SHARE, false)
        isTitleFixed = intent.getBooleanExtra(IS_TITLE_FIXED, false)
        mode = intent.getIntExtra(PARAM_MODE, MODE_DEFAULT)
    }

    private fun initTitleBar() {
        binding.titleBar.tvTitle.text = title
        if (isShare) binding.titleBar.ivShare.visible()
        binding.titleBar.ivShare.setOnClickListener { showDialogShare("${title}:${linkUrl}") }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.webView.settings.run {
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            javaScriptEnabled = true
            binding.webView.removeJavascriptInterface("searchBoxJavaBridge_")
            intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis())
            binding.webView.addJavascriptInterface(SonicJavaScriptInterface(sonicSessionClient, intent), "sonic")
            allowContentAccess = true
            databaseEnabled = true
            domStorageEnabled = true
            setAppCacheEnabled(true)
            savePassword = false
            saveFormData = false
            useWideViewPort = true
            loadWithOverviewMode = true
            defaultTextEncodingName = "UTF-8"
            setSupportZoom(true)
        }
        binding.webView.webChromeClient = UIWebChromeClient(binding, this)
        binding.webView.webViewClient = UIWebViewClient(binding, this)
        binding.webView.setDownloadListener { url, _, _, _, _ ->
            // 调用系统浏览器下载
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    /**
     * 使用VasSonic框架提升H5首屏加载速度。
     */
    private fun preloadInitVasSonic() {
        window.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)

        // init sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(SonicRuntimeImpl(application), SonicConfig.Builder().build())
        }

        // if it's sonic mode , startup sonic session at first time
        if (MODE_DEFAULT != mode) { // sonic mode
            val sessionConfigBuilder = SonicSessionConfig.Builder()
            sessionConfigBuilder.setSupportLocalServer(true)

            // if it's offline pkg mode, we need to intercept the session connection
            if (MODE_SONIC_WITH_OFFLINE_CACHE == mode) {
                sessionConfigBuilder.setCacheInterceptor(object : SonicCacheInterceptor(null) {
                    override fun getCacheData(session: SonicSession): String? {
                        return null // offline pkg does not need cache
                    }
                })
                sessionConfigBuilder.setConnectionInterceptor(object : SonicSessionConnectionInterceptor() {
                    override fun getConnection(session: SonicSession, intent: Intent): SonicSessionConnection {
                        return OfflinePkgSessionConnection(this@WebViewActivity, session, intent)
                    }
                })
            }

            // create sonic session and run sonic flow
            sonicSession = SonicEngine.getInstance().createSession(linkUrl, sessionConfigBuilder.build())
            if (null != sonicSession) {
                sonicSession?.bindClient(SonicSessionClientImpl().also { sonicSessionClient = it })
            } else {
                // this only happen when a same sonic session is already running,
                // u can comment following codes to feedback as a default mode.
                // throw new UnknownError("create session fail!");
                logD(TAG, "${title},${linkUrl}:create sonic session fail!")
            }
        }
    }

    class UIWebViewClient(val binding: ActivityWebViewBinding, val activity: WebViewActivity) : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            activity.linkUrl = url
            super.onPageStarted(view, url, favicon)
            binding.progressBar.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            activity.sonicSession?.sessionClient?.pageFinish(url)
            binding.progressBar.visibility = View.INVISIBLE
        }

        override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
            if (activity.sonicSession != null) {
                val requestResponse = activity.sonicSessionClient?.requestResource(request?.url.toString())
                if (requestResponse is WebResourceResponse) return requestResponse
            }
            return super.shouldInterceptRequest(view, request)
        }
    }

    class UIWebChromeClient(val binding: ActivityWebViewBinding, val activity: WebViewActivity) : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            if (!activity.isTitleFixed) {
                title?.run {
                    activity.title = this
                    binding.titleBar.tvTitle.text = this
                }
            }
        }
    }

    companion object {

        private const val TITLE = "title"

        private const val LINK_URL = "link_url"

        private const val IS_SHARE = "is_share"

        private const val IS_TITLE_FIXED = "isTitleFixed"

        const val MODE_DEFAULT = 0

        const val MODE_SONIC = 1

        const val MODE_SONIC_WITH_OFFLINE_CACHE = 2

        const val PARAM_MODE = "param_mode"

        const val DEFAULT_URL = "https://github.com/VIPyinzhiwei/Eyepetizer"

        val DEFAULT_TITLE = GlobalUtil.appName

        /**
         * 跳转WebView网页界面
         *
         * @param context       上下文环境
         * @param title         标题
         * @param url           加载地址
         * @param isShare       是否显示分享按钮
         * @param isTitleFixed  是否固定显示标题，不会通过动态加载后的网页标题而改变。true：固定，false 反之。
         * @param mode          加载模式：MODE_DEFAULT 默认使用WebView加载；MODE_SONIC 使用VasSonic框架加载； MODE_SONIC_WITH_OFFLINE_CACHE 使用VasSonic框架离线加载
         */
        fun start(context: Context, title: String, url: String, isShare: Boolean = true, isTitleFixed: Boolean = true, mode: Int = MODE_SONIC) {
            url.preCreateSession()  //预加载url
            val intent = Intent(context, WebViewActivity::class.java).apply {
                putExtra(TITLE, title)
                putExtra(LINK_URL, url)
                putExtra(IS_SHARE, isShare)
                putExtra(IS_TITLE_FIXED, isTitleFixed)
                putExtra(PARAM_MODE, mode)
                putExtra(SonicJavaScriptInterface.PARAM_CLICK_TIME, System.currentTimeMillis())
            }
            context.startActivity(intent)
        }
    }
}