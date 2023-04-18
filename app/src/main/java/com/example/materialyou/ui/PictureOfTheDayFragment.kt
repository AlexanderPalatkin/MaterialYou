package com.example.materialyou.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.materialyou.databinding.FragmentPictureOfTheDayBinding
import java.time.LocalDate

class PictureOfTheDayFragment : Fragment() {
    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PictureOfTheDayViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(
            viewLifecycleOwner
        ) {appState -> renderData(appState) }

        loadChipsClickListeners()

        binding.inputLayoutSearch.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditSearch.text.toString()}")
            })

        }
    }

    private fun loadChipsClickListeners() {
        binding.chipToday.setOnClickListener {
            viewModel.sendRequest("today")
            Toast.makeText(requireContext(), "Today", Toast.LENGTH_SHORT).show()
        }
        binding.chipYesterday.setOnClickListener {
            val yesterday = LocalDate.now().minusDays(1).toString()
            viewModel.sendRequest(yesterday)
            Toast.makeText(requireContext(), "Yesterday", Toast.LENGTH_SHORT).show()
        }
        binding.chipDayBeforeYesterday.setOnClickListener {
            val dayBeforeYesterday = LocalDate.now().minusDays(2).toString()
            viewModel.sendRequest(dayBeforeYesterday)
            Toast.makeText(requireContext(), "DayBeforeYesterday", Toast.LENGTH_SHORT).show()
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
                if (url.isEmpty()) {
                    toast("Link is empty")
                } else {
                    binding.imageView.load(url)
                }
            }
        }
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
}