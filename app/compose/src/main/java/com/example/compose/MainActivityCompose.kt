package com.example.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
  Surface(color = Color.Yellow, modifier = Modifier.fillMaxWidth()) {
    Text(
      text = "Hello2 $name!",
      modifier = Modifier.padding(24.dp),
      style = MaterialTheme.typography.h4
    )
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
  val counterState = remember { mutableStateOf(0) }

  Column(modifier = Modifier.fillMaxHeight()) {
    Column(modifier = Modifier.weight(1f)) {
      for (name in names) {
        Greeting(name = name)
        Divider(color = Color.Black)
      }
      Divider(color = Color.Transparent, thickness = 5.dp)
      Counter(
        count = counterState.value,
        updateCount = { newCount -> counterState.value = newCount }
      )
    }
  }
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
  Button(
    onClick = { updateCount(count + 1) },
    colors = ButtonConstants.defaultButtonColors(
      backgroundColor = if (count > 5) Color.Green else Color.Blue
    )
  ) {
    Text("I've been clicked $count times")
  }
}