package com.ribsky.ridna.utils.ext

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import androidx.core.graphics.ColorUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.ribsky.ridna.R
import java.io.File

class ImageExtension {

    companion object {

        private var factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()!!

        val Int.isDark: Boolean get() = ColorUtils.calculateLuminance(this) < 0.5

        fun ImageView.load(uri: Uri) {
            Glide.with(this.context)
                .load(uri)
                .error(R.drawable.ic_splash)
                .transition(withCrossFade(factory))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
        }

        fun ImageView.load(path: String) {

            val image = File(this.context.filesDir, path).path

            Glide.with(this.context)
                .load(image)
                .error(R.drawable.ic_splash)
                .transition(withCrossFade(factory))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
        }

        fun Context.getBitmapFromUri(uri: Uri, callback: ((Bitmap?) -> Unit)? = null) {

            Glide.with(this)
                .asBitmap()
                .load(uri)
                .centerCrop()
                .addListener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean = true

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        callback?.invoke(resource)
                        return true
                    }
                })
                .submit()
        }
    }
}
