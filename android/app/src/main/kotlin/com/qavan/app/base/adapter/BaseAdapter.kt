package com.qavan.app.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.qavan.app.data.model.*
import com.qavan.app.extensions.kotlin.launchOnUI

@Suppress("UNCHECKED_CAST")
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    var items: List<T> = mutableListOf()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return viewType(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return viewHolder(viewType, parent)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.onBind(position, items[position])
    }

    fun setData(newItems: List<T>) {
        launchOnUI {
            val diffCallback = object : DiffUtil.Callback() {

                override fun getOldListSize(): Int = items.size

                override fun getNewListSize(): Int = newItems.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return areItemsTheSame(items[oldItemPosition], newItems[newItemPosition])
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return items[oldItemPosition].hashCode() == newItems[newItemPosition].hashCode()
                }
            }
            val result = DiffUtil.calculateDiff(diffCallback)
            items = newItems
            result.dispatchUpdatesTo(this@BaseAdapter)
        }
    }

    fun appendData(newItems: List<T>) {
        setData(items + newItems)
    }

    fun appendData(newItem: T) {
        appendData(items + listOf(newItem))
    }

    abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    abstract fun viewHolder(viewType: Int, parent: ViewGroup): BaseViewHolder<T>

    abstract fun viewType(item: T): Int

}