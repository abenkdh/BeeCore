package com.beestudio.beecore

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.io.File


    fun ImageView.loadImageGif(url: String, listener: ((GifDrawable) -> Unit)? = null){
        Glide.with(this.context)
            .asGif()
            .load(url)
            .placeholder(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    listener?.invoke(resource!!)
                    return false
                }
            })
            .into(this)
    }

    fun ImageView.loadImage(url: String, listener: ((Bitmap) -> Unit)? = null){
        Glide.with(this.context)
            .asBitmap()
            .load(url)
            .placeholder(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    listener?.invoke(resource!!)
                    return false
                }
            })
            .into(this)
    }

    fun Context.downloadImage(url: String, listener: ((File) -> Unit)? = null){
        Glide.with(this)
            .download(url)
            .listener(object : RequestListener<File> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<File>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: File?,
                    models: Any?,
                    target: Target<File>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    listener?.invoke(resource!!)
                    return false
                }
            })
            .submit()
    }