package com.daniel.workmanagerapp.presentation.state

import androidx.compose.runtime.Immutable
import com.daniel.workmanagerapp.domain.entity.Article

@Immutable
data class NewsHomeState private constructor(
    val isLoading: Boolean = true,
    val articles: List<Article> = emptyList(),
    val error: String? = null
) {
    companion object {
        val initialState: NewsHomeState
            get() = NewsHomeState()
    }
}