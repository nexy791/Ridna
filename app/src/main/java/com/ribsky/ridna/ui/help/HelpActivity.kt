package com.ribsky.ridna.ui.help

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.ribsky.ridna.R
import com.ribsky.ridna.databinding.ActivityHelpBinding
import com.ribsky.ridna.navigator.Navigator
import com.ribsky.ridna.utils.ShareView
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.string
import dev.chrisbanes.insetter.applyInsetter
import org.koin.android.ext.android.inject

class HelpActivity : LocalizationActivity(R.layout.activity_help) {

    private val binding: ActivityHelpBinding by viewBinding()
    private val navigator: Navigator by inject()
    private var shareView: ShareView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() = with(binding) {
        appBarLayout.applyInsetter {
            type(statusBars = true) {
                margin()
            }
        }
        materialToolbar.setNavigationOnClickListener { onBackPressed() }
        btnRate.setOnClickListener {
            navigator.openRate(this@HelpActivity)
        }
        btnShare.setOnClickListener {
            shareApp()
        }
    }

    private fun shareApp() {
        shareView = ShareView
            .addContext(this)
            .addName(string(R.string.default_share_text).format(packageName))
            .build().apply {
                share(this@HelpActivity)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        shareView?.clear()
        shareView = null
    }
}
