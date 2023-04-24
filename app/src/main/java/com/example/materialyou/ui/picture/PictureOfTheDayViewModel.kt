package com.example.materialyou.ui.picture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.materialyou.BuildConfig
import com.example.materialyou.model.RepositoryImpl
import com.example.materialyou.model.dto.PictureOfTheDayResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) :
    ViewModel() {

    fun getLiveData(): LiveData<AppState> {
        return liveData
    }

    fun sendRequest(date: String) {
        liveData.value = AppState.Loading
        val apiKey = BuildConfig.NASA_API_KEY
        if (
            apiKey.isBlank()
        ) {
            liveData.value = AppState.Error(Throwable("You need API key"))
        } else {
            if (date == "Today") {
                repositoryImpl.getPictureOfTheDayApi().getPictureOfTheDay(apiKey).enqueue(callback)
            } else {
                repositoryImpl.getPictureOfTheDayApi().getPictureOfTheDayByDate(apiKey, date)
                    .enqueue(callback)
            }
        }
    }

    private val callback = object : Callback<PictureOfTheDayResponseData> {
        override fun onResponse(
            call: Call<PictureOfTheDayResponseData>,
            response: Response<PictureOfTheDayResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveData.value = AppState.Success(response.body()!!)
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveData.value =
                        AppState.Error(Throwable("Unidentified error"))
                } else {
                    liveData.value =
                        AppState.Error(Throwable(message))
                }
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
            liveData.value = AppState.Error(t)
        }
    }
}