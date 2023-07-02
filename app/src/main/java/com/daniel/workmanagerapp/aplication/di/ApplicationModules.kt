package com.daniel.workmanagerapp.aplication.di

import com.daniel.workmanagerapp.data.repository.NewsRepositoryImp
import com.daniel.workmanagerapp.domain.NewsRepository
import com.daniel.workmanagerapp.domain.usecases.GetArticlesUseCaseImpl
import com.daniel.workmanagerapp.domain.usecases.GetArticlesUseCase
import com.daniel.workmanagerapp.presentation.state.NewsHomeState
import com.daniel.workmanagerapp.presentation.viewmodel.NewsHomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ApplicationModules {

    val domainModule = module {

        factory<NewsRepository> {
            NewsRepositoryImp(
                dataSource = get(), scope = get()
            )
        }

        factory<GetArticlesUseCase> { GetArticlesUseCaseImpl(repository = get()) }
    }


    val presentationModule = module {
        viewModel {
            NewsHomeViewModel(
                initialState = NewsHomeState.initialState,
                useCase = get(),
                coroutineScope = get()
            )
        }
    }
}
