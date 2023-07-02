package com.daniel.workmanagerapp.presentation.ui.news

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.daniel.core.extensions.convertArticleDate
import com.daniel.workmanagerapp.R
import com.daniel.workmanagerapp.domain.entity.Article
import com.daniel.workmanagerapp.presentation.state.NewsHomeState
import com.daniel.workmanagerapp.presentation.viewmodel.NewsHomeViewModel


@Composable
fun NewsScreen(newsHomeViewModel: NewsHomeViewModel) {
    val context = LocalContext.current
    val state by newsHomeViewModel.state.collectAsState()

    NewsScreen(state = state, onArticleTap = { news ->
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(news.url)
        }
        context.startActivity(intent)
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(state: NewsHomeState, onArticleTap: (Article) -> Unit) {
    Scaffold(
        topBar = {
            TopBar()
        }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding), Alignment.Center
        ) {
            when {
                state.isLoading.not() && state.error != null -> {
                    OnError(error = state.error)
                }

                state.isLoading -> {
                    CircularProgressIndicator()
                }

                else -> {
                    ArticlesFeed(news = state.articles, onArticleTap)
                }
            }
        }
    }
}

@Composable
fun TopBar() {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = LocalContext.current.getString(R.string.stocks),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
private fun ArticlesFeed(news: List<Article>, onArticleTap: (Article) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)

    ) {
        itemsIndexed(news) { _, item ->
            ArticlesList(news = item, onArticleTap = onArticleTap)
        }
    }
}

@Composable
private fun OnError(error: String) {
    Text(text = error)
}

@Composable
private fun ArticlesList(news: Article, onArticleTap: (Article) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onArticleTap(news) }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            val model = ImageRequest.Builder(LocalContext.current)
                .data(
                    news.urlToImage.ifBlank {
                        LocalContext.current.getDrawable(R.drawable.baseline_article_24)
                    }
                )
                .diskCachePolicy(CachePolicy.ENABLED)
                .crossfade(1000)
                .build()
            val painter = rememberAsyncImagePainter(model)
            val state = painter.state
            val transition by animateFloatAsState(
                targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f, label = ""
            )
            Image(
                painter = painter,
                contentDescription = "custom transition based on painter state",
                modifier = Modifier
                    .alpha(transition)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp)),
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 6.dp), Arrangement.SpaceBetween
        ) {
            Text(
                text = news.author,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Text(
            text = String.convertArticleDate(news.publishedAt),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = news.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
        Divider(
            color = Color.Black,
            modifier = Modifier
                .padding(top = 2.dp, bottom = 2.dp)
                .fillMaxWidth()
                .height(3.dp)
        )
    }
}

