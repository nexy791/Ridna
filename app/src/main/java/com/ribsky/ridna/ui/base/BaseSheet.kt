package com.ribsky.ridna.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ribsky.ridna.R
import com.ribsky.ridna.navigator.Navigator
import com.ribsky.ridna.navigator.event.EventNavigator
import com.ribsky.ridna.ui.MainActivity
import org.koin.android.ext.android.inject

abstract class BaseSheet<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : BottomSheetDialogFragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    protected val navigator: Navigator by inject()

    protected val activity: MainActivity by lazy {
        requireActivity() as MainActivity
    }

    protected val eventNavigator: EventNavigator by lazy {
        requireActivity() as EventNavigator
    }

    override fun getTheme(): Int = R.style.BottomSheet

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator.navController = findNavController()
        initViews()
        initObserves()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        navigator.navController = null
        _binding = null
        clear()
    }

    abstract fun initViews()
    abstract fun initObserves()
    abstract fun clear()
}
