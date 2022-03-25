package com.ribsky.ridna.ui.view.goal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textview.MaterialTextView
import com.ribsky.ridna.R
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.string

class GoalView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val goals = List(400) { it * 50 }

    init {
        init(context)
    }

    private var _view: View? = null
    private var tvFirstDate: MaterialTextView? = null
    private var tvSecondDate: MaterialTextView? = null
    private var progress: LinearProgressIndicator? = null
    private var days: Int? = null

    fun setDays(days: Int) {

        this.days = days

        val firstDate = goals.lastOrNull { e -> days >= e } ?: goals.first()
        val lastDate = goals.firstOrNull { e -> days < e } ?: goals.last()

        tvFirstDate?.text = context.string(R.string.n_days).format(firstDate)
        tvSecondDate?.text = context.string(R.string.n_days).format(lastDate)

        val percent = ((days - firstDate) * 100 / 50)
        progress?.setProgressCompat(percent, true)
    }

    private fun init(context: Context) {
        _view = LayoutInflater.from(context).inflate(R.layout.layout_goal, this)
        tvFirstDate = findViewById(R.id.tv_date_first)
        tvSecondDate = findViewById(R.id.tv_date_last)
        progress = findViewById(R.id.progress_horizontal)
    }
}
