package com.example.numberslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumbersList()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NumbersList(
    modifier: Modifier = Modifier,
    viewModel: MyViewModel = viewModel()
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        GridLayout(
            modifier = modifier.weight(1f),
            items = viewModel.items,
        )

        Button(
            modifier = modifier.padding(16.dp).align(Alignment.End),
            onClick = { viewModel.addItem() },
        ) {
            Text(text = "+")
        }
    }
}

@Composable
fun GridLayout(modifier: Modifier = Modifier, items: List<Int>) {
    val configuration = LocalConfiguration.current
    val columns = if (configuration.orientation == 1) 3 else 4

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(items) { item ->
            Card(
                modifier = modifier.padding(8.dp).size(100.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(if (item % 2 == 0) Color.Red else Color.Blue),
            ) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = item.toString(),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}

class MyViewModel : ViewModel() {
    var items = mutableStateListOf<Int>()
        private set

    fun addItem() {
        items.add(items.size)
    }
}