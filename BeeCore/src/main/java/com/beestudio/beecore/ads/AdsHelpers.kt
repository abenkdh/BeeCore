package com.beestudio.beecore.ads

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.beestudio.beecore.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView

lateinit var FACEBOOK_BANNER_ID: String
lateinit var FACEBOOK_NATIVE_ID: String
lateinit var FACEBOOK_INTERSTITIAL_ID: String
lateinit var FACEBOOK_REWARD_ID: String
lateinit var ADMOB_NATIVE_ID: String
lateinit var ADMOB_BANNER_ID: String
lateinit var ADMOB_INTERSTITIAL_ID: String
lateinit var ADMOB_REWARD_ADS: String
lateinit var ADMOB_OPEN_ADS_ID: String

lateinit var STARTAPP_ID: String

var INTERSTITIAL_CLICK = 0




lateinit var unifiedNativeAd: UnifiedNativeAd
var isNativeLoaded: Boolean = false

lateinit var adViewFacebook: com.facebook.ads.AdView
lateinit var adViewAdmob: AdView
lateinit var interstitialAdAdmob: InterstitialAd
lateinit var interstitialAdFacebook: com.facebook.ads.InterstitialAd

@SuppressLint("MissingPermission")
fun Context.loadInterstitial(callback: () -> Unit){
    interstitialAdAdmob = InterstitialAd(this)
    interstitialAdAdmob.adUnitId = ADMOB_INTERSTITIAL_ID
    interstitialAdAdmob.loadAd(AdRequest.Builder().build())
    interstitialAdAdmob.adListener = object : AdListener() {
        override fun onAdFailedToLoad(p0: LoadAdError?) {
            super.onAdFailedToLoad(p0)
            callback.invoke()
        }
        
        override fun onAdLoaded() {
            super.onAdLoaded()
            callback.invoke()
        }
    }
}

var INTERSTITIAL_COUNT = 0
fun Context.showInterstitialWithCount(block: () -> Unit){
    INTERSTITIAL_COUNT++
    if(INTERSTITIAL_CLICK == INTERSTITIAL_COUNT){
        INTERSTITIAL_COUNT = 0
        showInterstitialAds {
            block.invoke()
        }
    } else {
        block.invoke()
    }
}

@SuppressLint("MissingPermission")
fun Context.showInterstitialAds(block: () -> Unit){
    interstitialAdAdmob.adListener = object : AdListener() {
        override fun onAdFailedToLoad(p0: LoadAdError?) {
            super.onAdFailedToLoad(p0)
            interstitialAdAdmob.loadAd(AdRequest.Builder().build())
            block.invoke()
        }
        
        override fun onAdClosed() {
            super.onAdClosed()
            interstitialAdAdmob.loadAd(AdRequest.Builder().build())
            block.invoke()
        }
    }
    if(interstitialAdAdmob.isLoaded) interstitialAdAdmob.show()
}

@SuppressLint("MissingPermission")
fun Context.createBannerAds(block : (View) -> Unit){
    adViewAdmob = AdView(this)
    adViewAdmob.adSize = getAdSize()
    adViewAdmob.adUnitId = ADMOB_BANNER_ID
    block.invoke(adViewAdmob)
    adViewAdmob.loadAd(AdRequest.Builder().build())
}

fun destroyBannerAds(){
    adViewAdmob.destroy()
}

fun Context.getAdSize(): com.google.android.gms.ads.AdSize? {
    val context = this as AppCompatActivity
    val display: Display = context.windowManager.defaultDisplay
    val outMetrics = DisplayMetrics()
    display.getMetrics(outMetrics)
    val widthPixels = outMetrics.widthPixels.toFloat()
    val density = outMetrics.density
    val adWidth = (widthPixels / density).toInt()
    return getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
}

@SuppressLint("InflateParams")
fun Context.makeAdsView(): View {
    val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val adView = inflater.inflate(R.layout.native_view, null) as UnifiedNativeAdView
    createAdmobNativeView(unifiedNativeAd, adView)
    return adView
}

@SuppressLint("InflateParams")
fun Context.createAdsView(view: FrameLayout){
    val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val adView = inflater.inflate(R.layout.native_view, null) as UnifiedNativeAdView
        createAdmobNativeView(unifiedNativeAd, adView)
        view.removeAllViews()
        view.addView(adView)
}
fun Context.loadNativeAds(){
        loadAdmobNative()
    
}

@SuppressLint("MissingPermission")
private fun Context.loadAdmobNative() {
    val builder = AdLoader.Builder(this, ADMOB_NATIVE_ID) //"/6499/example/native"
    builder.forUnifiedNativeAd {
        unifiedNativeAd = it
    }
    val adLoader = builder.withAdListener(object : AdListener() {
        override fun onAdLoaded() {
            super.onAdLoaded()
            isNativeLoaded = true
        }
        
        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
            isNativeLoaded = false
        }
    }).build()
    adLoader.loadAd(AdRequest.Builder().build())
}


fun createAdmobNativeView(nativeAd: UnifiedNativeAd, adView: UnifiedNativeAdView){
    adView.mediaView = adView.findViewById(R.id.ad_media)
    adView.headlineView = adView.findViewById(R.id.ad_headline)
    adView.bodyView = adView.findViewById(R.id.ad_body)
    adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
    adView.iconView = adView.findViewById(R.id.ad_app_icon)
    adView.priceView = adView.findViewById(R.id.ad_price)
    adView.starRatingView = adView.findViewById(R.id.ad_stars)
    adView.storeView = adView.findViewById(R.id.ad_store)
    adView.advertiserView = adView.findViewById(R.id.ad_advertiser)
    (adView.headlineView as TextView).text = nativeAd.headline
    adView.mediaView.setMediaContent(nativeAd.mediaContent)
    
    if (nativeAd.body == null) {
        adView.bodyView.visibility = View.GONE
    } else {
        adView.bodyView.visibility = View.VISIBLE
        (adView.bodyView as TextView).text = nativeAd.body
    }
    
    if (nativeAd.callToAction == null) {
        adView.callToActionView.visibility = View.GONE
    } else {
        adView.callToActionView.visibility = View.VISIBLE
        (adView.callToActionView as Button).text = nativeAd.callToAction
    }
    
    if (nativeAd.icon == null) {
        adView.iconView.visibility = View.GONE
    } else {
        (adView.iconView as ImageView).setImageDrawable(
            nativeAd.icon.drawable
        )
        adView.iconView.visibility = View.VISIBLE
    }
    
    if (nativeAd.price == null) {
        adView.priceView.visibility = View.GONE
    } else {
        adView.priceView.visibility = View.VISIBLE
        (adView.priceView as TextView).text = nativeAd.price
    }
    
    if (nativeAd.store == null) {
        adView.storeView.visibility = View.GONE
    } else {
        adView.storeView.visibility = View.VISIBLE
        (adView.storeView as TextView).text = nativeAd.store
    }
    
    if (nativeAd.starRating == null) {
        adView.starRatingView.visibility = View.GONE
    } else {
        (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
        adView.starRatingView.visibility = View.VISIBLE
    }
    
    if (nativeAd.advertiser == null) {
        adView.advertiserView.visibility = View.GONE
    } else {
        (adView.advertiserView as TextView).text = nativeAd.advertiser
        adView.advertiserView.visibility = View.VISIBLE
    }
    adView.setNativeAd(nativeAd)
}



