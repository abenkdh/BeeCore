package com.beestudio.beecore.ads


import android.app.Activity
import com.beestudio.beecore.inapp.BeePurchase
import com.facebook.ads.Ad
import com.facebook.ads.RewardedVideoAd
import com.facebook.ads.RewardedVideoAdListener
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback


private var rewardedAd: RewardedAd? = null
fun Activity.loadRewardAds(callback: () -> Unit) {
    if(BeePurchase.isPremium()){
        callback.invoke()
    } else {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(this, ADMOB_REWARD_ADS, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                rewardedAd = null
                callback.invoke()
            }

            override fun onAdLoaded(reward: RewardedAd) {
                rewardedAd = reward
                callback.invoke()
            }
        })
    }
}

fun Activity.showRewardAds(callback: () -> Unit) {
    if(BeePurchase.isPremium()){
        callback.invoke()
    } else {
        if (ADS_PROVIDER!!.lowercase() != "facebook") {
            rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {}

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    rewardedAd = null
                    loadRewardAds {
                        callback.invoke()
                    }
                }

                override fun onAdShowedFullScreenContent() {}
            }
            if (rewardedAd != null) {
                rewardedAd?.show(this@showRewardAds) {
                    loadRewardAds {
                        callback.invoke()
                    }
                }
            }
        } else {
            val rewardedAdFacebook = RewardedVideoAd(this, FACEBOOK_REWARD_ID)
            val rewardedVideoAdListener: RewardedVideoAdListener =
                object : RewardedVideoAdListener {
                    override fun onError(p0: Ad?, p1: com.facebook.ads.AdError?) {
                        callback.invoke()
                    }

                    override fun onAdLoaded(p0: Ad?) {
                        rewardedAdFacebook.show()
                    }

                    override fun onAdClicked(p0: Ad?) {
                    }

                    override fun onLoggingImpression(p0: Ad?) {
                    }

                    override fun onRewardedVideoCompleted() {
                    }

                    override fun onRewardedVideoClosed() {
                        callback.invoke()
                    }
                }
            rewardedAdFacebook.loadAd(
                rewardedAdFacebook.buildLoadAdConfig()
                    .withAdListener(rewardedVideoAdListener)
                    .build()
            )
        }
    }
}
