package com.qavan.app.base.adapter

import androidx.recyclerview.widget.RecyclerView

abstract class BaseInfiniteAdapter<T>(
    itemListOriginal: List<T>
) : RecyclerView.Adapter<BaseInfiniteViewHolder<T>>() {

    protected val items: List<T> = listOf(itemListOriginal.last()) +
        itemListOriginal +
        listOf(itemListOriginal.first())

    override fun onBindViewHolder(holder: BaseInfiniteViewHolder<T>, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.count()
}