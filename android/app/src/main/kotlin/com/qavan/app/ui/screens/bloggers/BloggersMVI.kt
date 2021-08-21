package com.qavan.app.ui.screens.bloggers

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.qavan.app.base.mvi.MVI
import com.qavan.app.data.repository.BloggersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BloggersMVI @Inject constructor(
    private val bloggersRepository: BloggersRepository,
): MVI<BloggersContract.Event, BloggersContract.State, BloggersContract.Effect>() {

    val bloggers by lazy {
        Pager(PagingConfig(pageSize = 30)) {
            bloggersRepository.source
        }.flow.cachedIn(viewModelScope)
    }

    private val _selectedBloggers by lazy { MutableStateFlow(listOf<Int>()) }
    val selectedBloggers by lazy { _selectedBloggers.asStateFlow() }

    override fun createInitialState(): BloggersContract.State {
        return BloggersContract.State(
            BloggersContract.BloggersState.Idle
        )
    }

    override fun handleEvent(event: BloggersContract.Event) {
        when(event) {
            is BloggersContract.Event.SelectBlogger -> {
                _selectedBloggers.value += event.blogger.id
            }
            is BloggersContract.Event.DeselectBlogger -> {
                _selectedBloggers.value -= event.blogger.id
            }
        }
    }

}