package com.ribsky.ridna.ui

import android.os.Bundle
import android.view.Menu
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.google.android.material.color.DynamicColors
import com.google.android.material.snackbar.Snackbar
import com.ribsky.ridna.R
import com.ribsky.ridna.databinding.ActivityMainBinding
import com.ribsky.ridna.navigator.Navigator
import com.ribsky.ridna.navigator.event.EventNavigator
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.action
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.alert
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.negativeButton
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.positiveButton
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.snackbar
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.string
import dev.chrisbanes.insetter.applyInsetter
import org.koin.android.ext.android.inject

class MainActivity : LocalizationActivity(R.layout.activity_main), EventNavigator {

    private val binding: ActivityMainBinding by viewBinding()
    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment).navController
    }

    private val navigator: Navigator by inject()
    private var isMenuHide = false
        set(value) {
            field = value
            invalidateMenu()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        DynamicColors.applyToActivityIfAvailable(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        initNavigation()
        initViews()
        initInsetters()
    }

    private fun initInsetters() = with(binding) {
        appBarLayout.applyInsetter {
            type(statusBars = true) {
                margin()
            }
        }
    }

    private fun initViews() = with(binding) {
        fabAdd.setOnClickListener {
            when (navController.currentDestination?.parent?.id) {
                R.id.nav_main -> navigator.addEventDialog()
                R.id.nav_relation -> navigator.editRelationDialog()
            }
        }
        topAppBar.apply {
            setOnMenuItemClickListener { menu ->
                if (menu.itemId == R.id.menu_settings) {
                    navigator.openSettings()
                }
                return@setOnMenuItemClickListener true
            }
        }
    }

    private fun initNavigation() = with(binding) {
        navigator.navController = navController
        navigationBar.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainFragment,
                R.id.relationFragment
            )
        )
        topAppBar.apply {
            setupWithNavController(navController, appBarConfiguration)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.parent?.id) {
                R.id.nav_main -> fabAdd.setImageResource(R.drawable.ic_round_add_24)
                R.id.nav_relation -> fabAdd.setImageResource(R.drawable.ic_outline_edit_24)
            }
            if (destination.id != R.id.settingsFragment && destination.id != R.id.libraryFragment) {
                fabAdd.show()
            } else {
                fabAdd.hide()
            }

            isMenuHide =
                destination.id == R.id.settingsFragment || destination.id == R.id.eventsFragment || destination.id == R.id.libraryFragment
        }
    }

    override fun onBackPressed() {
        if (!navController.navigateUp()) {
            alert {
                setIcon(R.drawable.ic_round_exit_to_app_24)
                setTitle(string(R.string.dialog_exit_title))
                setMessage(string(R.string.dialog_exit_body))
                negativeButton(string(R.string.dialog_exit_action)) {
                    finishAffinity()
                }
                positiveButton(string(R.string.dialog_exit_cancel))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_top_bar, menu)
        val item = menu.findItem(R.id.menu_settings)
        item.isVisible = !isMenuHide
        return super.onCreateOptionsMenu(menu)
    }

    override fun showSnackBar(text: String) {
        binding.root.snackbar(text, Snackbar.LENGTH_SHORT) {
            action(string(R.string.snack_ok)) {}
            anchorView = binding.fabAdd
        }
    }
}
