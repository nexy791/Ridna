package com.ribsky.ridna.ui.pick

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.ribsky.ridna.R
import com.ribsky.ridna.adapter.FadeInLinearLayoutManager
import com.ribsky.ridna.adapter.pick.PickEventAdapter
import com.ribsky.ridna.databinding.ActivityPickBinding
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.string
import dev.chrisbanes.insetter.applyInsetter

class PickEventActivity : LocalizationActivity(R.layout.activity_pick) {

    private val binding: ActivityPickBinding by viewBinding()
    private var adapter: PickEventAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initInsetters()
        initList()
        initToolbar()
    }

    private fun initToolbar() {
        binding.topAppBar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initInsetters() = with(binding) {
        appBarLayout.applyInsetter {
            type(statusBars = true) {
                margin()
            }
        }
        recyclerView.applyInsetter {
            type(navigationBars = true) {
                margin()
            }
        }
    }

    private fun initList() {
        adapter = PickEventAdapter { event ->
            setResult(RESULT_OK, intent.putExtra(KEY_EVENT, event))
            finish()
        }.apply {
            submitList(events)
        }

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = FadeInLinearLayoutManager(this@PickEventActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@PickEventActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = this@PickEventActivity.adapter
        }
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter = null
    }

    companion object {

        const val KEY_EVENT = "KEY_EVENT"

        private val Context.events
            get() = listOf(
                string(R.string.event_0),
                string(R.string.event_1),
                string(R.string.event_2),
                string(R.string.event_3),
                string(R.string.event_4),
                string(R.string.event_5),
                string(R.string.event_6),
                string(R.string.event_7),
                string(R.string.event_8),
                string(R.string.event_9),
                string(R.string.event_10),
                string(R.string.event_11),
                string(R.string.event_12),
                string(R.string.event_13),
                string(R.string.event_14)
            )
    }
}
