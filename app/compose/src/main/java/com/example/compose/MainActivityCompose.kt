package com.example.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.ui.GithubpagingTheme

class MainActivityCompose : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MyApp {
        MyScreenContent()
      }
    }
  }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
  GithubpagingTheme {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
      content()
    }
  }
}

@Composable
fun Greeting(name: String) {
  Surface(color = Color.Yellow) {
    Text(text = "Hello2 $name!", modifier = Modifier.padding(24.dp))
  }
}

@Preview("Text preview", showBackground = true)
@Composable
fun DefaultPreview() {
  MyApp {
    MyScreenContent()
  }
}

@Composable
fun MyScreenContent(names: List<String> = listOf("Android", "there")) {
  Column {
    for (name in names) {
      Greeting(name = name)
      Divider(color = Color.Black)
    }
  }
}