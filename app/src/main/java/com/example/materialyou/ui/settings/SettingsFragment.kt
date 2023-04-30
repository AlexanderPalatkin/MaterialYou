package com.example.materialyou.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.materialyou.ui.MainActivity
import com.example.materialyou.R
import com.example.materialyou.databinding.FragmentSettingsBinding
import com.example.materialyou.utils.KEY_CURRENT_THEME_LOCAL
import com.example.materialyou.utils.KEY_SP_LOCAL

class SettingsFragment : Fragment() {

    private val themePink = R.style.PinkTheme
    private val themeIndigo = R.style.IndigoTheme

    private lateinit var parentActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = requireActivity() as MainActivity
    }

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val context: Context = ContextThemeWrapper(activity, getRealStyleLocal(getCurrentThemeLocal()))
        val localInflater = inflater.cloneInContext(context)
        _binding = FragmentSettingsBinding.inflate(localInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(getCurrentThemeLocal()) {
            themePink -> binding.settingsChipPink.isChecked = true
            themeIndigo -> binding.settingsChipIndigo.isChecked = true
        }
        binding.settingsChipPink.setOnClickListener {
            setCurrentThemeLocal(themePink)
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SettingsFragment.newInstance())
                .commit()
        }
        binding.settingsChipIndigo.setOnClickListener {
            setCurrentThemeLocal(themeIndigo)
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SettingsFragment.newInstance())
                .commit()
        }

    }

    private fun setCurrentThemeLocal(currentTheme: Int){
        val sharedPreferences =
            requireActivity().getSharedPreferences(KEY_SP_LOCAL, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME_LOCAL, currentTheme)
        editor.apply()
    }

    private fun getCurrentThemeLocal(): Int {
        val sharedPreferences =
            requireActivity().getSharedPreferences(KEY_SP_LOCAL, AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME_LOCAL, -1)
    }

    private fun getRealStyleLocal(currentTheme: Int): Int {
        return when(currentTheme) {
            themePink -> R.style.PinkTheme
            themeIndigo -> R.style.IndigoTheme
            else -> 0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }

}