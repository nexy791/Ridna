package com.ribsky.ridna.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ribsky.ridna.R
import com.ribsky.ridna.adapter.library.LibraryAdapter
import com.ribsky.ridna.databinding.FragmentLibraryBinding
import com.ribsky.ridna.model.library.LibraryModel
import com.ribsky.ridna.navigator.Navigator
import com.ribsky.ridna.ui.MainActivity
import org.koin.android.ext.android.inject

class LibraryFragment : Fragment(R.layout.fragment_library) {

    private val binding: FragmentLibraryBinding by viewBinding(CreateMethod.INFLATE)
    private val navigator: Navigator by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = LibraryAdapter { link ->
            navigator.openWeb(requireActivity() as MainActivity, link)
        }.apply {
            submitList(LibraryModel.list)
        }
    }
}
