package com.qavan.app.ui.screens.bloggers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.qavan.app.compose.Loading
import com.qavan.app.compose.buttons.AppButton
import com.qavan.app.data.model.Blogger
import com.qavan.app.ui.items.ItemBlogger


@Composable
fun BloggersScreen(
    state: BloggersContract.BloggersState,
    bloggers: LazyPagingItems<Blogger>,
    selectedBloggers: List<Int>,
    onAddBloggerClick: () -> Unit = {},
    onBloggerClick: (Boolean, Blogger) -> Unit = { _, _ ->},
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppButton(
            text = "Добавить блогера в ручную",
            onClick = onAddBloggerClick,
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(bloggers) { blogger ->
                if (blogger != null) {
                    ItemBlogger(
                        modifier = Modifier.fillMaxWidth(),
                        blogger = blogger,
                        checked = blogger.ID in selectedBloggers,
                        onBloggerClick = {
                            onBloggerClick(blogger.ID in selectedBloggers, it)
                        }
                    )
                } else {
                    Loading()
                }
            }
            when {
                bloggers.loadState.append is LoadState.Loading || bloggers.loadState.refresh is LoadState.Loading -> {
                    item {
                        Loading()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun BloggersScreenPreview() {
//    AppTheme(true) {
//        BloggersScreen(
//            state = BloggersContract.BloggersState.Idle,
//        )
//    }
}