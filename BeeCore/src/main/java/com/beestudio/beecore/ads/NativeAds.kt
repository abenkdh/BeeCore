package com.beestudio.beecore.ads

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRatingBar
import com.beestudio.beecore.inapp.BeePurchase
import com.beestudio.beecore.R
import com.beestudio.beecore.log
import com.facebook.ads.Ad
import com.facebook.ads.NativeAd
import com.facebook.ads.NativeAdListener
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAdView
import com.makeramen.roundedimageview.RoundedImageView

private var nativeAds: com.google.android.gms.ads.nativead.NativeAd? = null
private var facebookNativeAd: com.facebook.ads.NativeAd? = null
private var isNativeLoaded: Boolean = false

@SuppressLint("InflateParams")
fun Context.setupNativeView(view: ViewGroup) {
    if(BeePurchase.isPremium()) {
        if (isNativeLoaded) {
            if (ADS_PROVIDER?.lowercase() == "facebook") {
                createFacebookNativeView(view)
            } else {
                createAdmobNativeView(view)
            }
        }
    }
}

fun Context.loadNativeAds(callback: () -> Unit) {
    if(BeePurchase.isPremium()){
        callback.invoke()
    } else {
        if (ADS_PROVIDER?.lowercase() == "facebook") {
            val nativeAd = NativeAd(this, FACEBOOK_NATIVE_ID)
            nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                    .withAdListener(object : NativeAdListener {
                        override fun onAdClicked(p0: Ad?) {
                        }

                        override fun onMediaDownloaded(p0: Ad?) {
                        }

                        override fun onError(p0: Ad?, p1: com.facebook.ads.AdError) {
                            isNativeLoaded = false
                            callback.invoke()
                        }

                        override fun onAdLoaded(p0: Ad?) {
                            facebookNativeAd = nativeAd
                            isNativeLoaded = true
                            callback.invoke()
                        }

                        override fun onLoggingImpression(p0: Ad?) {
                        }
                    })
                    .build()
            )
        } else {
            MobileAds.initialize(this)
            val nativeLoader = AdLoader.Builder(this, ADMOB_NATIVE_ID)
                .forNativeAd { nativeAd ->
                    isNativeLoaded = true
                    nativeAds = nativeAd
                    callback.invoke()
                }
                .withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        callback.invoke()
                        adError.message.log()
                    }
                }).build()
            nativeLoader.loadAd(AdRequest.Builder().build())
        }
    }
}

fun Context.createFacebookNativeView(view: ViewGroup) {
    val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val adView = inflater.inflate(R.layout.native_view_facebook, null) as LinearLayout
    facebookNativeAd?.unregisterView()
    val nativeAdIcon: com.facebook.ads.MediaView = adView.findViewById(R.id.native_ad_icon)
    val nativeAdTitle: TextView = adView.findViewById(R.id.native_ad_title)
    val nativeAdMedia: com.facebook.ads.MediaView = adView.findViewById(R.id.native_ad_media)
    val nativeAdSocialContext: TextView = adView.findViewById(R.id.native_ad_social_context)
    val nativeAdBody: TextView = adView.findViewById(R.id.native_ad_body)
    val sponsoredLabel: TextView = adView.findViewById(R.id.native_ad_sponsored_label)
    val nativeAdCallToAction: Button = adView.findViewById(R.id.native_ad_call_to_action)

    nativeAdTitle.text = facebookNativeAd?.advertiserName
    nativeAdBody.text = facebookNativeAd?.adBodyText
    nativeAdSocialContext.text = facebookNativeAd?.adSocialContext
    nativeAdCallToAction.visibility = if (facebookNativeAd!!.hasCallToAction()) View.VISIBLE else View.INVISIBLE
    nativeAdCallToAction.text = facebookNativeAd?.adCallToAction
    sponsoredLabel.text = facebookNativeAd?.sponsoredTranslation
    val clickableViews: MutableList<View> = ArrayList()
    clickableViews.add(nativeAdTitle)
    clickableViews.add(nativeAdCallToAction)
    facebookNativeAd?.registerViewForInteraction(
        adView, nativeAdMedia, nativeAdIcon, clickableViews
    )
    view.removeAllViews()
    view.addView(adView)
}

fun createAdmobNativeView(parent: ViewGroup) {
    val inflater =
        parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val adView = inflater.inflate(R.layout.native_view, null) as NativeAdView

    val headlineView = adView.findViewById<TextView>(R.id.ad_headline)
    val mediaView = adView.findViewById<MediaView>(R.id.ad_media)
    val bodyView = adView.findViewById<TextView>(R.id.ad_body)
    val callToActionView = adView.findViewById<Button>(R.id.ad_call_to_action)
    val iconView = adView.findViewById<RoundedImageView>(R.id.ad_app_icon)
    val priceView = adView.findViewById<TextView>(R.id.ad_price)
    val starRatingView = adView.findViewById<AppCompatRatingBar>(R.id.ad_stars)
    val storeView = adView.findViewById<TextView>(R.id.ad_store)
    val advertiserView = adView.findViewById<TextView>(R.id.ad_advertiser)

    headlineView.text = nativeAds?.headline
    adView.headlineView = headlineView

    mediaView.setMediaContent(nativeAds?.mediaContent!!)
    adView.mediaView = mediaView

    bodyView.text = nativeAds?.body
    adView.bodyView = bodyView

    callToActionView.text = nativeAds?.callToAction
    adView.callToActionView = callToActionView

    iconView.setImageDrawable(nativeAds?.icon!!.drawable)
    adView.iconView = iconView

    priceView.text = nativeAds?.price
    adView.priceView = priceView

    starRatingView.rating = nativeAds?.starRating!!.toFloat()
    adView.starRatingView = starRatingView

    storeView.text = nativeAds?.store
    adView.storeView = storeView

    advertiserView.text = nativeAds?.advertiser
    adView.advertiserView = advertiserView

    adView.setNativeAd(nativeAds!!)

    parent.removeAllViews()
    parent.addView(adView)
}