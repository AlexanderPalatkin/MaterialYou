package com.example.materialyou.ui.picture

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.text.style.TypefaceSpan
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.materialyou.R
import com.example.materialyou.databinding.FragmentPictureOfTheDayStartBinding
import com.example.materialyou.ui.MainActivity
import com.example.materialyou.ui.drawer.BottomNavigationActivity
import com.example.materialyou.ui.drawer.BottomNavigationDrawerFragment
import com.example.materialyou.ui.settings.SettingsFragment
import com.example.materialyou.ui.viewpager.ViewPagerActivity
import com.example.materialyou.utils.KEY_CURRENT_THEME_LOCAL
import com.example.materialyou.utils.KEY_SP_LOCAL
import com.example.materialyou.utils.WIKI_URL
import java.time.LocalDate

class PictureOfTheDayFragment : Fragment() {
    private var _binding: FragmentPictureOfTheDayStartBinding? = null
    private val binding get() = _binding!!

    private var buttonExplainIsChecked = false

    private val duration = 2000L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        when (getCurrentThemeLocal()) {
            R.style.PinkTheme -> requireActivity().setTheme(R.style.PinkTheme)
            R.style.IndigoTheme -> requireActivity().setTheme(R.style.IndigoTheme)
            R.style.OrangeTheme -> requireActivity().setTheme(R.style.OrangeTheme)
            R.style.GreenTheme -> requireActivity().setTheme(R.style.GreenTheme)
        }

        _binding = FragmentPictureOfTheDayStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PictureOfTheDayViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(
            viewLifecycleOwner
        ) { appState -> renderData(appState) }

        loadChipsClickListeners()
        loadWikiSearchClickListener()

        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
    }

    private fun loadChipsClickListeners() {

        binding.chipToday.setOnClickListener {
            viewModel.sendRequest(getString(R.string.today))
        }

        binding.chipYesterday.setOnClickListener {
            val yesterday = LocalDate.now().minusDays(1).toString()
            viewModel.sendRequest(yesterday)
        }

        binding.chipDayBeforeYesterday.setOnClickListener {
            val dayBeforeYesterday = LocalDate.now().minusDays(2).toString()
            viewModel.sendRequest(dayBeforeYesterday)
        }

        binding.fab.setOnClickListener {
            buttonExplainIsChecked = !buttonExplainIsChecked
            if (buttonExplainIsChecked) {
                binding.imageView.animate().alpha(0.05f).duration = duration
                binding.tvExplanation.animate().alpha(1f).duration = duration
            } else {
                binding.imageView.animate().alpha(1f).duration = duration
                binding.tvExplanation.animate().alpha(0f).duration = duration
            }
        }
    }

    private fun loadWikiSearchClickListener() {
        binding.inputLayoutSearch.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("${WIKI_URL}${binding.inputEditSearch.text.toString()}")
            })
        }
    }

    private fun renderData(data: AppState) {

        when (data) {
            is AppState.Error -> {
                toast(data.error.message)
            }
            is AppState.Loading -> {}
            is AppState.Success -> {
                val url = data.pictureOfTheDayResponseData.url
                val titleText = data.pictureOfTheDayResponseData.title
                val explanationText = data.pictureOfTheDayResponseData.explanation
                if (url.isEmpty() || titleText.isEmpty() || explanationText.isEmpty()) {
                    toast(getString(R.string.link_is_empty))
                } else {
                    buttonExplainIsChecked = false

                    binding.imageView.load(url)
                    binding.tvExplanationTitle.text = titleText

                    binding.tvExplanationTitle.typeface = Typeface.createFromAsset(
                        requireContext().assets,
                        "KeplerStdSemiboldScnItSubh.otf"
                    )

                    val spannableStringBuilderExplanationText =
                        SpannableStringBuilder(explanationText)
                    binding.tvExplanation.setText(
                        spannableStringBuilderExplanationText,
                        TextView.BufferType.EDITABLE
                    )
                    val spannableExplanationText =
                        binding.tvExplanation.text as SpannableStringBuilder

                    initSpan(spannableExplanationText)

                    animateOnSuccessRenderData()
                }
            }
        }
    }

    private fun initSpan(spannable: SpannableStringBuilder) {

        val verticalAlignment = DynamicDrawableSpan.ALIGN_BASELINE
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_earth)!!
        val widthInPx = 20
        val heightInPx = 20
        drawable.setBounds(0, 0, widthInPx, heightInPx)
        for (i in spannable.indices) {
            if (spannable[i] == 'o') {
                spannable.setSpan(
                    ImageSpan(drawable, verticalAlignment),
                    i,
                    i + 1,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )
            }
        }

        val startExplanationText = getString(R.string.start_explanation_text)
        spannable.insert(0, startExplanationText)

        val requestCallback = FontRequest(
            "com.google.android.gms.fonts",
            "com.google.android.gms",
            "Lora",
            R.array.com_google_android_gms_fonts_certs
        )
        val callback = object : FontsContractCompat.FontRequestCallback() {
            override fun onTypefaceRetrieved(typeface: Typeface?) {
                typeface?.let {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        spannable.setSpan(
                            TypefaceSpan(it),
                            startExplanationText.length,
                            spannable.length,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE
                        )
                    }
                }
            }
        }
        val handler = Handler(Looper.getMainLooper())
        FontsContractCompat.requestFont(requireContext(), requestCallback, callback, handler)
    }

    private fun animateOnSuccessRenderData() {
        binding.tvExplanationTitle.animate().alpha(1f).duration = duration
        binding.imageView.animate().alpha(1f).duration = duration
        binding.tvExplanation.animate().alpha(0f).duration = duration
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_bar_telescope -> {
                activity?.let { startActivity(Intent(it, BottomNavigationActivity::class.java)) }
            }
            R.id.action_bar_fav -> {
                activity?.let { startActivity(Intent(it, ViewPagerActivity::class.java)) }
            }
            R.id.action_bar_settings -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .hide(this)
                    .add(R.id.container, SettingsFragment.newInstance())
                    .addToBackStack("pictureOfTheDayFragment")
                    .commit()
            }
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getCurrentThemeLocal(): Int {
        val sharedPreferences =
            requireActivity().getSharedPreferences(KEY_SP_LOCAL, AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME_LOCAL, -1)
    }


}