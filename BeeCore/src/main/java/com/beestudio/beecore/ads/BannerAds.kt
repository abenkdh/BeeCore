package com.beestudio.beecore.ads

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsets
import android.view.WindowMetrics
import com.beestudio.beecore.inapp.BeePurchase
import com.facebook.ads.AdSize
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

lateinit var adViewFacebook: com.facebook.ads.AdView
lateinit var adViewAdmob: AdView
@SuppressLint("MissingPermission")
fun Context.loadBannerAds(block: (View) -> Unit) {
    if(BeePurchase.isPremium()){
        block.invoke(View(this))
    } else {
        if (ADS_PROVIDER?.lowercase() == "facebook") {
            adViewFacebook = com.facebook.ads.AdView(this, FACEBOOK_BANNER_ID, AdSize.BANNER_HEIGHT_50)
            block.invoke(adViewFacebook)
            adViewFacebook.loadAd()
        } else {
            adViewAdmob = AdView(this)
            adViewAdmob.adSize = adSize
            adViewAdmob.adUnitId = ADMOB_BANNER_ID
            block.invoke(adViewAdmob)
            adViewAdmob.loadAd(AdRequest.Builder().build())
        }
    }
}

fun destroyBannerAds() {
    if (ADS_PROVIDER?.lowercase() == "facebook") {
        adViewFacebook.destroy()
    } else {
        adViewAdmob.destroy()
    }
}

val Context.adSize: com.google.android.gms.ads.AdSize?
    get() {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics: WindowMetrics = (this as Activity).windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            val adWidth = resources.displayMetrics.widthPixels - insets.left - insets.right
            com.google.android.gms.ads.AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                this,
                adWidth
            )
        } else {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val adWidth = resources.displayMetrics.widthPixels / resources.displayMetrics.density
                com.google.android.gms.ads.AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                    this,
                    adWidth.toInt()
                )
            } else {
                val outMetrics = DisplayMetrics()
                val widthPixels = outMetrics.widthPixels.toFloat()
                val density = outMetrics.density
                val adWidth = (widthPixels / density).toInt()
                com.google.android.gms.ads.AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                    this,
                    adWidth
                )
            }
        }
    }