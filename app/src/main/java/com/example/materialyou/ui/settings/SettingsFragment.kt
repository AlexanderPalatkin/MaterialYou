package com.example.materialyou.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.materialyou.MainActivity
import com.example.materialyou.R
import com.example.materialyou.databinding.FragmentPictureOfTheDayBinding
import com.example.materialyou.databinding.FragmentSettingsBinding
import com.example.materialyou.ui.drawer.BottomNavigationDrawerFragment
import com.example.materialyou.utils.WIKI_URL
import java.time.LocalDate

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}