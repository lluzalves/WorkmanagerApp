package com.daniel.workmanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.daniel.workmanagerapp.presentation.ui.news.NewsScreen
import com.daniel.workmanagerapp.presentation.ui.theme.NewsAppTheme
import com.daniel.workmanagerapp.presentation.viewmodel.NewsHomeViewModel
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val newsHomeViewModel: NewsHomeViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Content()
                }
            }
        }
    }

    @Composable
    fun Content() {
        NewsScreen(newsHomeViewModel = newsHomeViewModel)
    }

    @Preview(showBackground = true)
    @Composable
    fun ContentPreview() {
        NewsAppTheme {
            Content()
        }
    }
}