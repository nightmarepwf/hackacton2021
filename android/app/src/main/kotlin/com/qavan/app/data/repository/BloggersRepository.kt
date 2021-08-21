package com.qavan.app.data.repository

import com.qavan.app.data.source.remote.BloggersDataSource

interface BloggersRepository {
    val source: BloggersDataSource
}

class BloggersRepositoryImpl(
    override val source: BloggersDataSource,
): BaseRepository(), BloggersRepository {

}