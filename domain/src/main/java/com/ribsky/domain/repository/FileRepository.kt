package com.ribsky.domain.repository

interface FileRepository {

    suspend fun createFile(uri: String): Result<String>
}
