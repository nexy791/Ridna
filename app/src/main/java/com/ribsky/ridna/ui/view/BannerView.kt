package com.ribsky.ridna.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import com.cesarferreira.tempo.Tempo
import com.google.android.material.textview.MaterialTextView
import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.ridna.R
import com.ribsky.ridna.utils.ext.DateExtension.Companion.diffInDays

class BannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    fun interface Callback {
        fun onClick(date: Long)
    }

    init {
        init(context)
    }

    private var callback: Callback? = null
    private var eventDate: Long? = null
    private var _view: View? = null
    private var tvDays: MaterialTextView? = null

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun setEvent(eventModel: BaseEventModel) {
        eventDate = eventModel.date
        tvDays?.text = context.diffInDays(Tempo.now.time, eventDate!!)
    }

    private fun init(context: Context) {
        _view = LayoutInflater.from(context).inflate(R.layout.layout_banner, this)
        tvDays = findViewById(R.id.tv_days)
        _view?.setOnClickListener {
            callback?.onClick(eventDate!!)
        }
    }
}
