package com.daniel.workmanagerapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.workmanagerapp.domain.usecases.GetArticlesUseCase
import com.daniel.workmanagerapp.presentation.state.NewsHomeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch

open class NewsHomeViewModel(
    initialState: NewsHomeState,
    private val useCase: GetArticlesUseCase,
    private val coroutineScope: CoroutineScope
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)

    val state = _state.asStateFlow()

    fun setState(update: NewsHomeState.() -> NewsHomeState) = _state.updateAndGet(update)

    init {
        viewModelScope.launch(coroutineScope.coroutineContext) {
            val result = useCase.getArticlesResult()
            result.onSuccess { articlesResult ->
                setState {
                    copy(
                        isLoading = false,
                        articles = articlesResult.headlines
                    )
                }
            }
            result.onFailure {
                setState {
                    copy(
                        isLoading = false,
                        error = it.localizedMessage
                    )
                }
            }

        }
    }

}
