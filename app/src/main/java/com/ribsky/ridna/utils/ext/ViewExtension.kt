package com.ribsky.ridna.utils.ext

import android.content.Context
import android.content.DialogInterface
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ViewExtension {

    companion object {

        fun Fragment.alert(dialogBuilder: MaterialAlertDialogBuilder.() -> Unit) {
            requireContext().alert(dialogBuilder)
        }

        fun Context.alert(
            dialogBuilder: MaterialAlertDialogBuilder.() -> Unit
        ) {
            MaterialAlertDialogBuilder(this)
                .apply {
                    setCancelable(true)
                    dialogBuilder()
                    create()
                    show()
                }
        }

        fun MaterialAlertDialogBuilder.negativeButton(
            text: String,
            handleClick: (dialogInterface: DialogInterface) -> Unit = { it.dismiss() }
        ) {
            this.setNegativeButton(text) { dialogInterface, _ -> handleClick(dialogInterface) }
        }

        fun MaterialAlertDialogBuilder.positiveButton(
            text: String,
            handleClick: (dialogInterface: DialogInterface) -> Unit = { it.dismiss() }
        ) {
            this.setPositiveButton(text) { dialogInterface, _ -> handleClick(dialogInterface) }
        }

        fun View.snackbar(
            @StringRes messageRes: Int,
            length: Int = Snackbar.LENGTH_LONG,
            f: Snackbar.() -> Unit
        ) {
            snackbar(resources.getString(messageRes), length, f)
        }

        fun View.snackbar(
            message: String,
            length: Int = Snackbar.LENGTH_LONG,
            f: Snackbar.() -> Unit
        ) {
            val snack = Snackbar.make(this, message, length)
            snack.f()
            snack.show()
        }

        fun Snackbar.action(
            @StringRes actionRes: Int,
            color: Int? = null,
            listener: (View) -> Unit
        ) {
            action(view.resources.getString(actionRes), color, listener)
        }

        fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
            setAction(action, listener)
            color?.let { setActionTextColor(ContextCompat.getColor(context, color)) }
        }

        fun Fragment.string(@StringRes stringRes: Int) = requireContext().string(stringRes)

        fun Context.string(@StringRes stringRes: Int) = getString(stringRes)

        fun Fragment.color(@ColorRes colorRes: Int) = requireContext().color(colorRes)

        fun Context.color(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)

        @ColorInt
        fun Context.colorAttr(
            @AttrRes attrColor: Int,
            typedValue: TypedValue = TypedValue(),
            resolveRefs: Boolean = true
        ): Int {
            theme.resolveAttribute(attrColor, typedValue, resolveRefs)
            return typedValue.data
        }

        @ColorInt
        fun Fragment.colorAttr(
            @AttrRes attrColor: Int,
            typedValue: TypedValue = TypedValue(),
            resolveRefs: Boolean = true
        ): Int = requireContext().colorAttr(attrColor, typedValue, resolveRefs)
    }
}
