package com.example.newbankdemo.presentation.news

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class NewsActivity : ComponentActivity() {

    override fun onStart() {
        super.onStart()
        setContent {
            NewsScreen()
        }
    }
}

@Composable
fun NewsScreen() {
    Column {
        Text(text = "Hello, this is your News Screen!")
    }
}
