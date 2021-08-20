package com.qavan.app.extensions

import com.qavan.app.base.mvi.BaseFragmentMVI
import com.qavan.app.base.mvi.BaseModelViewIntent

typealias MVIOLD<VS, OSE> = BaseModelViewIntent<VS, OSE>
typealias FMVI<VS, OSE> = BaseFragmentMVI<VS, OSE>