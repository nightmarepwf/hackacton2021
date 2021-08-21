package com.qavan.app.data.repository

import com.qavan.app.data.source.local.UserDataSource

interface UserRepository: UserDataSource

class UserRepositoryImpl constructor(
    private val userDataSource: UserDataSource,
): BaseRepository(), UserRepository {

    override var userId: String
        get() = userDataSource.userId
        set(value) {
            userDataSource.userId = value
        }

}