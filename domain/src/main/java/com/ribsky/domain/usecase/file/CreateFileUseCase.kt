package com.ribsky.domain.usecase.file

import com.ribsky.domain.repository.FileRepository

interface CreateFileUseCase {

    suspend fun invoke(uri: String): Result<String>
}

class CreateFileUseCaseImpl(
    private val fileRepository: FileRepository
) : CreateFileUseCase {

    override suspend fun invoke(uri: String): Result<String> = fileRepository.createFile(uri)
}
