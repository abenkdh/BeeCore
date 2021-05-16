package com.beestudio.beecore

import android.app.Activity
import android.util.Base64
import com.androidnetworking.AndroidNetworking
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

var purchaseCode: String? = null
var purchaseName: String? = null
var purchaseUrl: String? = null
private var data: CubeGamming? = null
@Suppress("BlockingMethodInNonBlockingContext")
class CubeRequest {
    companion object {
        fun show(activity: Activity){
            val mBottomSheetDialog = BottomSheetMaterialDialog.Builder(activity)
                .setTitle("UHVyY2hhc2UgY29kZSBub3QgdmFsaWQ=".decode())
                .setMessage("SW0gc29ycnkgeW91ciBwdXJjaGFzZSBjb2RlIGlzIG5vdCB2YWxpZA==".decode())
                .setCancelable(false)
                .setPositiveButton(
                    "T3JkZXIgU291cmNl".decode()
                ) { _, _ ->
                    activity.startBrowser("aHR0cHM6Ly9jb2RlY2FueW9uLm5ldC9pdGVtL2JzLXdhbGxwYXBlci1oZC1hbmRyb2lkLXdhbGxwYXBlci1hcHAvMjUwMjk5NzI=".decode())
                }
                .setNegativeButton(
                    "Tm8sIFRoYW5rcw==".decode()
                ) { _, _ ->
                    activity.finish()
                }
                .build()
            mBottomSheetDialog.show()
        }

        fun String.decode(): String {
            return Base64.decode(this, Base64.DEFAULT).toString(charset("UTF-8"))
        }
    }
    suspend fun request() {
        coroutineScope {
            if(purchaseCode == null) throw Exception("UGxlYXNlIGRvbid0IGRlbGV0ZSBwdXJjaGFzZSBjb2Rl".decode())
            if(purchaseName == null) throw Exception("UGxlYXNlIGRvbid0IGRlbGV0ZSBwdXJjaGFzZSBuYW1l".decode())
            val req = withContext(Dispatchers.IO){
                AndroidNetworking.get("aHR0cHM6Ly9hcGkuZW52YXRvLmNvbS92My9tYXJrZXQvYXV0aG9yL3NhbGU=".decode())
                    .addHeaders("QXV0aG9yaXphdGlvbg==".decode(), "QmVhcmVyIDAzMUNtOTRWQkZXVkl3T0d1eXZmVGN2dm12RjNFTTli".decode())
                    .addHeaders("VXNlci1BZ2VudA==".decode(), "UHVyY2hhc2UgY29kZSB2ZXJpZmljYXRpb24gb24gYmVua2tzdHVkaW8ueHl6".decode())
                    .addQueryParameter("Y29kZQ==".decode(), purchaseCode)
                    .build().executeForString()
            }
            if(req.isSuccess){
                val adapter = createAdapter(CubeGamming::class.java)
                data = adapter.fromJson(req.result.toString())!!
            }
        }
    }


    fun isValidate(): Boolean {
        if(data == null){
            return false
        }
        return data!!.buyer == purchaseName && data!!.item?.url == purchaseUrl?.decode()
    }

    private fun <T>createAdapter(type: Class<T>) : JsonAdapter<T> {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build().adapter(type)
    }
    
    fun String.decode(): String {
        return Base64.decode(this, Base64.DEFAULT).toString(charset("UTF-8"))
    }
}