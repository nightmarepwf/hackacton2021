package com.qavan.app.ui.screens.bloggers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.qavan.app.compose.Loading
import com.qavan.app.compose.buttons.AppButton
import com.qavan.app.compose.text.AppTextH2
import com.qavan.app.data.model.Blogger
import com.qavan.app.data.model.IBlogger
import com.qavan.app.ui.items.ItemBlogger


@Composable
fun BloggersScreen(
    checkable: Boolean = false,
    state: BloggersContract.BloggersState,
    bloggers: LazyPagingItems<IBlogger>,
    selectedBloggers: List<Int>,
    onAddBloggerClick: () -> Unit = {},
    onBloggerClick: (Boolean, IBlogger) -> Unit = { _, _ ->},
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (bloggers.itemCount > 0) {
            item {
                AppTextH2(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(),
                    text = "Список блоггеров",
                    textAlign = TextAlign.Start,
                )
            }
            item {
                AppButton(
                    modifier = Modifier.padding(),
                    text = "Добавить блоггера вручную",
                    onClick = onAddBloggerClick,
                )
            }
        }
        items(bloggers) { blogger ->
            if (blogger != null) {
                ItemBlogger(
                    modifier = Modifier.fillMaxWidth(),
                    checkable = checkable,
                    blogger = blogger,
                    checked = blogger.id in selectedBloggers,
                    onBloggerClick = {
                        onBloggerClick(blogger.id in selectedBloggers, it)
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

@Preview
@Composable
private fun BloggersScreenPreview() {
//    AppTheme(true) {
//        BloggersScreen(
//            state = BloggersContract.BloggersState.Idle,
//        )
//    }
}