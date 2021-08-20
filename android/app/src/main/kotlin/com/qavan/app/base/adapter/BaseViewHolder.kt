package com.qavan.app.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(
    binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun onBind(position: Int, item: T)

}