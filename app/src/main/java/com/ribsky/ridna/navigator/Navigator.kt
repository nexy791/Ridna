package com.ribsky.ridna.navigator

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController

interface Navigator {

    var navController: NavController?

    fun addEventDialog()

    fun openEventDetails(id: Int)

    fun openAllEvents()

    fun editRelationDialog()

    fun openHelp(activity: AppCompatActivity)

    fun openRate(activity: AppCompatActivity)

    fun updateApp(activity: AppCompatActivity)

    fun openPrivacy(activity: AppCompatActivity)

    fun openLibraries()

    fun openAppSettings(activity: AppCompatActivity)

    fun openSettings()

    fun openWeb(activity: AppCompatActivity, link: String)
}
