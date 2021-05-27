package com.beestudio.beecore.base

import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


@Suppress("unused", "MemberVisibilityCanBePrivate", "RedundantOverride", "RedundantVisibilityModifier")
abstract class BaseRVAdapter<T, V : ViewBinding> @JvmOverloads constructor(data: ArrayList<T>? = null): RecyclerView.Adapter<BaseViewHolder<T, V>>(){
    var data: ArrayList<T> = data ?: arrayListOf()
    protected abstract fun viewHolder(parent: ViewGroup): BaseViewHolder<T, V>

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<T, V>) {
        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder<T, V>) {
        super.onViewAttachedToWindow(holder)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, @LayoutRes layout: Int): BaseViewHolder<T, V> {
        return viewHolder(viewGroup)
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder<T, V>, position: Int) {
        viewHolder.setupData(data[position])
    }

    open fun setupData(list: List<T>?) {
        if (list === this.data) {
            return
        }
        this.data = list as ArrayList<T>
        notifyDataSetChanged()
    }

    open fun addData(@IntRange(from = 0) position: Int, data: T) {
        this.data.add(position, data)
        notifyItemInserted(position)
    }
    
    override fun getItemCount(): Int {
        return data.size
    }
}
