package com.ribsky.ridna.di

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module

val prefsDi = module {

    single { provideSharedPrefs(context = get()) }
    single { moshi }
}

val moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()!!

fun provideSharedPrefs(context: Context): SharedPreferences {
    return context.getSharedPreferences("ridna.prefs", Context.MODE_PRIVATE)
}
