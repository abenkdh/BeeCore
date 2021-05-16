package com.beestudio.beecore

import android.app.Activity
import android.content.Intent
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.TransactionDetails

class BeePurchase(
    private val activity: Activity,
    private val onProductPurchased: ((Boolean) -> Unit)? = null,
    private val onPurchaseHistoryRestored: (() -> Unit)? = null,
    private val onBillingError: (() -> Unit)? = null,
    private val onBillingInitialized: () -> Unit)  {
    private lateinit var billingProcessor: BillingProcessor
    
    companion object {
        var googlePlayLicenseKey: String? = null
        var googlePlayMerchantId: String? = null
        var productId: String? = null
        fun isPremium(): Boolean {
            return BeePref.readBoolean("billingProcessor", false)
        }
    }
    
    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        return billingProcessor.handleActivityResult(requestCode, resultCode, data)
    }
    
    fun isIabServiceAvailable(): Boolean {
        return BillingProcessor.isIabServiceAvailable(activity)
    }
    
    fun purchase() {
        if(billingProcessor.isInitialized) {
            billingProcessor.purchase(activity, productId)
        } else {
            beeLogger("biling not initialized")
        }
    }
    
    init {
        billingProcessor = BillingProcessor.newBillingProcessor(activity, googlePlayLicenseKey, googlePlayMerchantId, object :
            BillingProcessor.IBillingHandler {
            override fun onProductPurchased(productId: String, details: TransactionDetails?) {
                BeePref.write("billingProcessor", billingProcessor.isValidTransactionDetails(details))
                onProductPurchased?.invoke(billingProcessor.isValidTransactionDetails(details))
            }
            
            override fun onPurchaseHistoryRestored() {
                onPurchaseHistoryRestored?.invoke()
            }
            
            override fun onBillingError(errorCode: Int, error: Throwable?) {
                onBillingError?.invoke()
            }
            
            override fun onBillingInitialized() {
                billingProcessor.getPurchaseTransactionDetails(productId).let {
                    try {
                        beeLogger(it?.purchaseInfo?.purchaseData?.productId !!)
                        BeePref.write("billingProcessor", true)
                    } catch(e: Exception) {
                        onBillingInitialized.invoke()
                        BeePref.write("billingProcessor", false)
                        beeLogger("asdasdasd")
                    }
                }
            }
        })
        billingProcessor.initialize()
    }
}