package com.qavan.app.base.dialog

import androidx.databinding.ViewDataBinding
import com.qavan.app.base.mvi.BaseFragmentMVI
import com.qavan.app.base.oneshotevent.BaseOneShotEvent
import com.qavan.app.base.viewstate.BaseViewState

abstract class BaseMainFullScreenFragmentDialog<T: ViewDataBinding, VS: BaseViewState, OSE: BaseOneShotEvent, FMVI: BaseFragmentMVI<VS, OSE>>(
    layoutId: Int,
): BaseMainFragmentDialog<T, VS, OSE, FMVI>(layoutId) {

//    override fun getTheme(): Int = R.style.FullscreenDialogTheme

}