package com.beestudio.beecore.ads
//
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.Context
//import androidx.annotation.NonNull
//import com.beestudio.beecore.inapp.BeePurchase
//import com.facebook.ads.Ad
//import com.facebook.ads.InterstitialAdListener
//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.FullScreenContentCallback
//import com.google.android.gms.ads.LoadAdError
//import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
//import com.startapp.android.publish.adsCommon.StartAppAd
//import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener
//import com.startapp.android.publish.adsCommon.adListeners.AdEventListener
//
//private var interstitialAdAdmob: com.google.android.gms.ads.interstitial.InterstitialAd? = null
//lateinit var interstitialAdFacebook: com.facebook.ads.InterstitialAd
//@SuppressLint("MissingPermission")
//
//fun Context.loadInterstitial(callback: () -> Unit) {
//    if(BeePurchase.isPremium()){
//        callback.invoke()
//    } else {
//            val adRequest: AdRequest = AdRequest.Builder().build()
//            com.google.android.gms.ads.interstitial.InterstitialAd.load(
//                this,
//                ADMOB_INTERSTITIAL_ID,
//                adRequest,
//                object : InterstitialAdLoadCallback() {
//                    override fun onAdLoaded(@NonNull interstitialAd: com.google.android.gms.ads.interstitial.InterstitialAd) {
//                        interstitialAdAdmob = interstitialAd
//                        callback.invoke()
//                    }
//
//                    override fun onAdFailedToLoad(@NonNull loadAdError: LoadAdError) {
//                        interstitialAdAdmob = null
//                        callback.invoke()
//                    }
//                })
//
//    }
//}
//
//
//private var INTERSTITIAL_COUNT = 0
//
//fun Context.showInterstitialWithCount(block: () -> Unit) {
//    if(BeePurchase.isPremium()){
//        block.invoke()
//    } else {
//        INTERSTITIAL_COUNT++
//        if (INTERSTITIAL_CLICK == INTERSTITIAL_COUNT) {
//            INTERSTITIAL_COUNT = 0
//            showInterstitialAds {
//                block.invoke()
//            }
//        } else {
//            block.invoke()
//        }
//    }
//}
//
//@SuppressLint("MissingPermission")
//fun Context.showInterstitialAds(block: () -> Unit) {
//    if(BeePurchase.isPremium()){
//        block.invoke()
//    } else {
//        if (ADS_PROVIDER?.lowercase() == "facebook") {
//            interstitialAdFacebook = com.facebook.ads.InterstitialAd(
//                this,
//                FACEBOOK_INTERSTITIAL_ID
//            )
//            val interstitialAdListener = object : InterstitialAdListener {
//                override fun onInterstitialDisplayed(p0: Ad?) {
//                }
//
//                override fun onAdClicked(p0: Ad?) {
//                }
//
//                override fun onInterstitialDismissed(p0: Ad?) {
//                    block.invoke()
//                }
//
//                override fun onError(p0: Ad?, p1: com.facebook.ads.AdError) {
//                    block.invoke()
//                }
//
//                override fun onAdLoaded(p0: Ad?) {
//                    if (interstitialAdFacebook.isAdLoaded) {
//                        interstitialAdFacebook.show()
//                    }
//                }
//
//                override fun onLoggingImpression(p0: Ad?) {
//                }
//            }
//            interstitialAdFacebook.loadAd(
//                interstitialAdFacebook.buildLoadAdConfig()
//                    .withAdListener(interstitialAdListener)
//                    .build()
//            )
//        } else if(ADS_PROVIDER?.lowercase() == "startapp") {
//            val startAppAd = StartAppAd(this)
//            startAppAd.loadAd(object : AdEventListener {
//                override fun onReceiveAd(p0: com.startapp.android.publish.adsCommon.Ad?) {
//                    startAppAd.showAd(object : AdDisplayListener {
//                        override fun adHidden(p0: com.startapp.android.publish.adsCommon.Ad?) {
//                            block.invoke()
//                        }
//
//                        override fun adDisplayed(p0: com.startapp.android.publish.adsCommon.Ad?) {
//                        }
//
//                        override fun adClicked(p0: com.startapp.android.publish.adsCommon.Ad?) {
//                        }
//
//                        override fun adNotDisplayed(
//                            p0: com.startapp.android.publish.adsCommon.Ad?) {
//                            block.invoke()
//                        }
//                    })
//                }
//
//                override fun onFailedToReceiveAd(p0: com.startapp.android.publish.adsCommon.Ad?) {
//                    block.invoke()
//                }
//            })
//
//        } else {
//            interstitialAdAdmob?.setFullScreenContentCallback(object : FullScreenContentCallback() {
//                override fun onAdDismissedFullScreenContent() {
//                    interstitialAdAdmob = null
//                    loadInterstitial {
//                        block.invoke()
//                    }
//                }
//
//                override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError?) {
//                    interstitialAdAdmob = null
//                    loadInterstitial {
//                        block.invoke()
//                    }
//                }
//
//                override fun onAdShowedFullScreenContent() {}
//            })
//            if (interstitialAdAdmob != null) {
//                interstitialAdAdmob?.show(this as Activity)
//            } else {
//                block.invoke()
//            }
//        }
//    }
//}
//
//
