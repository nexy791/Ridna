package com.ribsky.ridna.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import com.ribsky.ridna.R

class BannerViewAll @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    fun interface Callback {
        fun onClick()
    }

    private var callback: Callback? = null
    private var _view: View? = null

    init {
        init(context)
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    private fun init(context: Context) {
        _view = LayoutInflater.from(context).inflate(R.layout.layout_banner_all, this)
        _view?.setOnClickListener {
            callback?.onClick()
        }
    }
}
