package com.yeterkarakus.miniyoutube.view.searchpage.trackdetailsfragment

import android.webkit.WebView
import com.airbnb.lottie.LottieAnimationView

class WebViewClient(val lottieAnimationView: LottieAnimationView) : WebViewClient(){
    override fun onPageFinished(view: WebView?, url: String?){
        super.onPageFinished(view, url)
        lott
    }

}