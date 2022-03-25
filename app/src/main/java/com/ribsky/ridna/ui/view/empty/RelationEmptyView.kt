package com.ribsky.ridna.ui.view.empty

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import com.google.android.material.button.MaterialButton
import com.ribsky.ridna.R

class RelationEmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    fun interface Callback {
        fun onAddEvent()
    }

    init {
        init(context)
    }

    private var callback: Callback? = null
    private var btnAddEvent: MaterialButton? = null
    private var _view: View? = null

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    private fun init(context: Context) {
        _view = LayoutInflater.from(context).inflate(R.layout.layout_empty_relation, this)
        btnAddEvent = _view?.findViewById(R.id.btn_add_event)

        btnAddEvent?.setOnClickListener {
            callback?.onAddEvent()
        }
    }
}
