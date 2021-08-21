package com.qavan.app.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseInfiniteViewHolder<T>(
    binding: ViewDataBinding,
): RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: T, position: Int)

}