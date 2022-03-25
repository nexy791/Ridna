package com.ribsky.ridna.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.recyclerview.widget.LinearLayoutManager

open class FadeInLinearLayoutManager : LinearLayoutManager {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    private val enterInterpolator = AnticipateOvershootInterpolator(1.5f)

    override fun addView(child: View, index: Int) {
        super.addView(child, index)
        with(child) {
            alpha = 0.3f
            animate().alpha(1f).setInterpolator(enterInterpolator).duration = 500L
        }
    }
}
