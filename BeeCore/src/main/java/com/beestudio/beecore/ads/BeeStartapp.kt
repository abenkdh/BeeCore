package com.beestudio.beecore.ads

import android.app.Activity
import android.content.Context
import com.startapp.android.publish.adsCommon.StartAppAd
import com.startapp.android.publish.adsCommon.StartAppSDK

class BeeStartapp {
    companion object {
        fun initialize(activity: Activity){
            StartAppSDK.init(activity, STARTAPP_ID, true)
            StartAppAd.disableSplash()
        }
    }
}