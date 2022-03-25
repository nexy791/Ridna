package com.ribsky.ridna.navigator

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.ribsky.ridna.R
import com.ribsky.ridna.ui.dialog.event.EventDialog
import com.ribsky.ridna.ui.help.HelpActivity

class NavigatorImpl : Navigator {

    override var navController: NavController? = null

    override fun addEventDialog() {
        navController?.navigate(R.id.addEventDialog)
    }

    override fun openEventDetails(id: Int) {
        navController?.navigate(R.id.eventDialog, bundleOf(EventDialog.KEY_ID to id))
    }

    override fun openAllEvents() {
        navController?.navigate(R.id.eventsFragment)
    }

    override fun editRelationDialog() {
        navController?.navigate(R.id.editRelationDialog)
    }

    override fun openHelp(activity: AppCompatActivity) {
        activity.startActivity(Intent(activity, HelpActivity::class.java))
    }

    override fun openRate(activity: AppCompatActivity) {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://play.google.com/store/apps/details?id=${activity.packageName}")
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(Intent.createChooser(this, null))
        }
    }

    override fun updateApp(activity: AppCompatActivity) {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://play.google.com/store/apps/details?id=${activity.packageName}")
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(Intent.createChooser(this, null))
        }
    }

    override fun openPrivacy(activity: AppCompatActivity) {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://github.com/nexy791/ridna/blob/master/policy.md")
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(Intent.createChooser(this, null))
        }
    }

    override fun openLibraries() {
        navController?.navigate(R.id.libraryFragment)
    }

    override fun openAppSettings(activity: AppCompatActivity) {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:${activity.packageName}")
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(Intent.createChooser(this, null))
        }
    }

    override fun openSettings() {
        navController?.navigate(R.id.settingsFragment)
    }

    override fun openWeb(activity: AppCompatActivity, link: String) {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse(link)
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(Intent.createChooser(this, null))
        }
    }
}
