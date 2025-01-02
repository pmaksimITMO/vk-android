package com.example.apireader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.apireader.domain.ReaderViewModel
import com.example.apireader.domain.State
import com.example.apireader.model.Image
import com.example.apireader.ui.theme.APIReaderTheme

class MainActivity : ComponentActivity() {
    private val viewModel = ReaderViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APIReaderTheme {
                when(val state = viewModel.state) {
                    is State.Loading -> {
                        LoadingViewState()
                    }
                    is State.Error -> {
                        ErrorViewState { viewModel.retry() }
                    }
                    is State.Success -> {
                        ImagesViewState(state.data)
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingViewState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(50.dp))
    }
}

@Composable
fun ErrorViewState(onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
fun ImagesViewState(images: List<Image>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(images) { item ->
            AsyncImage(
                model = item.url,
                contentDescription = null,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}