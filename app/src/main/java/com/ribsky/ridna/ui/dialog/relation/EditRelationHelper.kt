package com.ribsky.ridna.ui.dialog.relation

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class EditRelationHelper(
    private val registry: ActivityResultRegistry,
    private val tag: String,
    private val callback: Callback
) : DefaultLifecycleObserver {

    interface Callback {
        fun onSuccess(id: EditRelationDialog.ImageType, uri: Uri)
        fun onError()
    }

    private var getContent: ActivityResultLauncher<String>? = null
    private var id: EditRelationDialog.ImageType? = null

    override fun onCreate(owner: LifecycleOwner) {
        getContent = registry.register(
            tag, owner,
            ActivityResultContracts.GetContent()
        ) { uri ->

            if (uri != null) {
                callback.onSuccess(id!!, uri)
            } else {
                callback.onError()
            }
        }
    }

    fun open(id: EditRelationDialog.ImageType) {
        this.id = id
        getContent?.launch("image/*")
    }
}
