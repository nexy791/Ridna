package com.ribsky.data.repository

import android.content.Context
import androidx.core.net.toUri
import com.ribsky.data.service.file.FileService
import com.ribsky.domain.repository.FileRepository
import java.util.*

class FileRepositoryImpl(
    context: Context,
    private val fileService: FileService
) : FileRepository {

    private val path = context.filesDir.path

    override suspend fun createFile(uri: String): Result<String> {

        val name = UUID.randomUUID().toString()

        return fileService.createFile(
            name = name,
            path = path,
            uri = uri.toUri()
        )
    }
}
