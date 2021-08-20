package com.qavan.app.compose

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.qavan.app.R


fun LazyListScope.itemBottomSpacer() {
    item {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_bottom_nav_button) * 3))
    }
}

@Composable
fun ColumnScope.BottomSpacer() {
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_bottom_nav_button) * 3))
}