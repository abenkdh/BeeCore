package com.beestudio.beecore.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T, V : ViewBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root){
    constructor(
        parent: ViewGroup,
        provider: (LayoutInflater, ViewGroup?, Boolean) -> V
    ) : this(provider.invoke(LayoutInflater.from(parent.context), parent, false))
    val context: Context = binding.root.context
    @Throws(Exception::class)
    abstract fun setupData(data: T)
}

