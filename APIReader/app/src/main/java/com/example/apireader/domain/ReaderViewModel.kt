package com.example.apireader.domain

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apireader.api.HttpService
import com.example.apireader.model.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReaderViewModel: ViewModel() {
    private var _state = mutableStateOf<State>(State.Loading)

    private var isLoading = false
    private val apiKey = "Irn4lbL7juKuESX3Tlj237YDIfIIwCi4"


    val state: State
        get() = _state.value

    init {
        loadImages()
    }

    fun retry() = loadImages()

    private fun loadImages() {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                Log.i("Main", "Start fetching")
                val images = withContext(Dispatchers.IO) {
                    val response = HttpService.giphyApi.getTrendyGifsByRating(apiKey = apiKey)
                    response.data.map { Image(it.images.original.url) }
                }
                Log.i("Main", images.toString())
                _state.value = State.Success(images)
            } catch (e : Exception) {
                Log.e("Main", e.message.toString())
                _state.value = State.Error
            } finally {
                isLoading = false
            }
        }
    }
}

sealed class State {
    data object Loading: State()
    data object Error: State()
    data class Success(val data: List<Image>): State()
}