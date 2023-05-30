package com.example.materialyou.ui.drawer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.materialyou.R
import com.example.materialyou.databinding.BottomNavigationLayoutBinding
import com.example.materialyou.ui.note.NoteActivity
import com.example.materialyou.ui.recycler.RecyclerActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {
    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> {
                    activity?.let {
                        startActivity(
                            Intent(
                                it,
                                RecyclerActivity::class.java
                            )
                        )
                    }
                }
                R.id.navigation_two ->
                    activity?.let {
                        startActivity(
                            Intent(
                                it,
                                NoteActivity::class.java
                            )
                        )
                    }
            }
            dismiss()
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
