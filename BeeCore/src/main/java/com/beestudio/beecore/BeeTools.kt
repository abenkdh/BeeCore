package com.beestudio.beecore

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import java.util.*

fun Any.log(tag: String? = "ABENK"){
    Log.e("$tag : ", "" + this)
}

fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.startBrowser(string: String): Unit =
    this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(string)))


fun Context.startShareApp(string: String) {
    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(Intent.EXTRA_TEXT, string)
    sendIntent.type = "text/plain"
    this.startActivity(sendIntent)
}

fun Context.startRate(): Unit =
    this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + this.packageName)))

inline fun <reified T : Activity> Context.startAct(): Unit =
    this.startActivity(newIntent<T>())


inline fun <reified T : Activity> Context.startAct(extras: Bundle): Unit =
    this.startActivity(newIntent<T>(extras))

inline fun <reified T : Context> Context.newIntent(extras: Bundle): Intent =
    newIntent<T>(0, extras)

inline fun <reified T : Context> Context.newIntent(flags: Int, extras: Bundle): Intent {
    val intent = newIntent<T>(flags)
    intent.putExtras(extras)
    return intent
}

inline fun <reified T : Context> Context.newIntent(): Intent =
    Intent(this, T::class.java)

inline fun <reified T : Context> Context.newIntent(flags: Int): Intent {
    val intent = newIntent<T>()
    intent.flags = flags
    return intent
}

@Suppress("DEPRECATION")
fun isFromPlaystore(context: Context): Boolean {
    val validInstallers: List<String> = ArrayList(listOf("com.android.vending", "com.google.android.feedback"))
    val installer = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        context.packageManager.getInstallSourceInfo(context.packageName).toString()
    } else {
        context.packageManager.getInstallerPackageName(context.packageName)
    }
    return installer != null && validInstallers.contains(installer)
}

fun getHandler(): Handler{
    return Handler(Looper.getMainLooper())
}
