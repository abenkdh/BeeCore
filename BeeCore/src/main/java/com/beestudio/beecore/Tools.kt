package com.beestudio.beecore

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import java.util.*

fun Context.startBrowser(string: String): Unit =
    this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(string)))

fun Context.startShareApp(string: String) {
    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(Intent.EXTRA_TEXT, string)
    sendIntent.type = "text/plain"
    this.startActivity(sendIntent)
}


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
