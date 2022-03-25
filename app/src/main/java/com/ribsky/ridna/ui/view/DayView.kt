package com.ribsky.ridna.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.core.view.isGone
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.ridna.R
import com.ribsky.ridna.adapter.day.ThisDayAdapter
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.string
import me.relex.circleindicator.CircleIndicator2

class DayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    interface Callback : ThisDayAdapter.Callback {
        fun onAddEvent()
    }

    init {
        init(context)
    }

    private var callback: Callback? = null
    private var _view: View? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: ThisDayAdapter? = null
    private var indicator: CircleIndicator2? = null
    private var layoutEmpty: View? = null

    fun setCallback(callback: Callback) {
        this.callback = callback
        initAdapter()
    }

    fun submitAdapter(events: List<BaseEventModel>) {
        adapter?.submitList(events)
        val isEmpty = events.isEmpty()
        layoutEmpty?.isGone = !isEmpty

        indicator?.isGone = isEmpty
        recyclerView?.isGone = isEmpty
    }

    private fun init(context: Context) {
        _view = LayoutInflater.from(context).inflate(R.layout.layout_this_day, this)

        initViews()
        initRecycler()
        initEmptyScreen()
    }

    private fun initEmptyScreen() {
        val btnOpen: MaterialButton = layoutEmpty!!.findViewById(R.id.btn_open)
        val tvThisDay: MaterialTextView = layoutEmpty!!.findViewById(R.id.tv_this_day)
        val tvDays: MaterialTextView = layoutEmpty!!.findViewById(R.id.tv_days)
        val btnShare: MaterialButton = layoutEmpty!!.findViewById(R.id.btn_share)

        btnOpen.isGone = true
        tvThisDay.text = context.string(R.string.empty_day_title)
        tvDays.text = context.string(R.string.empty_day_description)
        btnShare.apply {
            text = context.string(R.string.empty_day_add)
            setIconResource(R.drawable.ic_round_arrow_forward_24)
            setOnClickListener {
                callback?.onAddEvent()
            }
        }
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recycler_view)
        indicator = findViewById(R.id.indicator)
        layoutEmpty = findViewById(R.id.layout_empty)
    }

    private fun initAdapter() {
        adapter = callback?.let { call -> ThisDayAdapter(call) }
        recyclerView?.adapter = adapter
        adapter?.registerAdapterDataObserver(indicator!!.adapterDataObserver)
    }

    private fun initRecycler() {
        val snapHelper = PagerSnapHelper()
        val lm = FlexboxLayoutManager(context).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
            flexWrap = FlexWrap.NOWRAP
        }

        recyclerView?.apply {
            setHasFixedSize(false)
            layoutManager = lm
            snapHelper.attachToRecyclerView(this)
            indicator?.attachToRecyclerView(this, snapHelper)
        }
    }
}
