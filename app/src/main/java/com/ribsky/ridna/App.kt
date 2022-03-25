package com.ribsky.ridna

import android.content.Context
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import com.google.android.material.color.DynamicColors
import com.ribsky.ridna.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.*

class App : LocalizationApplication() {

    override fun getDefaultLanguage(context: Context): Locale = Locale.ENGLISH

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)

        startKoin {
            androidContext(this@App)
            modules(listOf(uiDi, domainDi, dataDi, dbDi, prefsDi))
        }
    }
}
