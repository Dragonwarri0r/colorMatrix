package com.dragonwarrior.colormatrix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.dragonwarrior.colormatrix.ui.theme.ColorMatrixTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorMatrixTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    View()
                }
            }
        }
    }
}

val scaleMatrix = mutableStateOf(
    ColorMatrix(
        floatArrayOf(
            1f, 0f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f, 0f,
            0f, 0f, 1f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        )
    )
)

val selected: MutableState<FloatArray> = mutableStateOf(
    floatArrayOf(
        1f, 0f, 0f, 0f, 0f,
        0f, 1f, 0f, 0f, 0f,
        0f, 0f, 1f, 0f, 0f,
        0f, 0f, 0f, 1f, 0f
    )
)

@Composable
fun View(modifier: Modifier = Modifier) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.test_img),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(Dp(200F)),
            colorFilter = ColorFilter.colorMatrix(scaleMatrix.value)
        )

        Row(row = 0)
        Row(row = 1)
        Row(row = 2)
        Row(row = 3)


        Button(onClick = {
            scaleMatrix.value = ColorMatrix(
                floatArrayOf(
                    1f, 0f, 0f, 0f, 0f,
                    0f, 1f, 0f, 0f, 0f,
                    0f, 0f, 1f, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
            selected.value = floatArrayOf(
                1f, 0f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f, 0f,
                0f, 0f, 1f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        }, modifier = Modifier.align(alignment = CenterHorizontally)) {
            Text(text = "reset")
        }

    }
}

const val size = 5

@Composable
fun Slider(modifier: Modifier = Modifier, index: Int, row: Int) {
    val modifier = Modifier
        .fillMaxWidth((1f / (size.toFloat() - index)))
    Column(modifier = modifier) {
        Text(
            text = selected.value[index + (row * size)].toString(),
            modifier = Modifier
                .padding(Dp(0f), Dp(12f), Dp(0f), Dp(0f))
                .align(alignment = CenterHorizontally)
        )

        Slider(value = selected.value[index + (row * size)].let {
            if (index == size - 1) {
                it / 255f
            } else {
                it
            }
        }, onValueChange = {
            val array = selected.value.clone()
            array[index + (row * size)] = if (index == size - 1) {
                it * 255
            } else {
                it
            }
            selected.value = array

            calcColorMatrix()
        },
        valueRange = -1f..1f)
    }
}

@Composable
fun Row(modifier: Modifier = Modifier, row: Int) {
    Row {
        for (i in 0 until size) {
            Slider(modifier = modifier, i, row)
        }
    }
}

fun calcColorMatrix() {
    scaleMatrix.value = ColorMatrix(selected.value)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ColorMatrixTheme {
        View()
    }
}