package com.ribsky.data.service.file

import android.net.Uri

interface FileService {

    fun createFile(name: String, path: String, uri: Uri): Result<String>
}
