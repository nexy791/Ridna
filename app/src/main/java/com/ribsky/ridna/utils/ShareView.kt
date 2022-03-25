package com.ribsky.ridna.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.core.content.FileProvider
import com.ribsky.ridna.R
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.string
import java.io.File
import java.util.*

@SuppressLint("StaticFieldLeak")
class ShareView(private val builder: Builder) {

    companion object Builder {

        val Context.footerText: String get() = string(R.string.footer_text).format(packageName)

        private var name: String? = null
        private var view: View? = null
        private var context: Context? = null

        private var file: File? = null

        fun addName(name: String) = apply {
            this.name = name
        }

        fun addView(view: View) = apply {
            this.view = view
        }

        fun addContext(context: Context) = apply {
            this.context = context
        }

        fun build(): ShareView {
            if (view != null) buildImage()
            return ShareView(this)
        }

        private fun buildImage() {

            val image =
                Bitmap.createBitmap(view!!.width, view!!.height, Bitmap.Config.ARGB_8888)

            view!!.draw(Canvas(image))

            runCatching {
                val file = File(context?.cacheDir, UUID.randomUUID().toString() + ".png")

                file.outputStream().use { out ->
                    image.compress(Bitmap.CompressFormat.PNG, 100, out)
                    out.flush()
                }

                this.file = file
            }
        }

        private fun createShareIntent(context: Context, file: File? = null) {

            val stickerAssetUri =
                file?.let { FileProvider.getUriForFile(context, context.packageName, it) }

            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, name)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                putExtra(Intent.EXTRA_STREAM, stickerAssetUri)
                type = "image/*"
            }

            context.startActivity(Intent.createChooser(shareIntent, null))
        }
    }

    fun share(context: Context) = createShareIntent(context, builder.file)

    fun clear() {
        name = null
        view = null
        file = null
        context = null
    }
}
