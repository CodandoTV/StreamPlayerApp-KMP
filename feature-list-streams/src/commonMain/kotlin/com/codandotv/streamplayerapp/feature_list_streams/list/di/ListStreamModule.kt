package com.codandotv.streamplayerapp.feature_list_streams.list.di

import com.codandotv.streamplayerapp.feature_list_streams.list.data.ListStreamRepository
import com.codandotv.streamplayerapp.feature_list_streams.list.data.ListStreamRepositoryImpl
import com.codandotv.streamplayerapp.feature_list_streams.list.data.ListStreamService
import com.codandotv.streamplayerapp.feature_list_streams.list.data.ListStreamServiceImpl
import com.codandotv.streamplayerapp.feature_list_streams.list.domain.GetGenresUseCase
import com.codandotv.streamplayerapp.feature_list_streams.list.domain.GetGenresUseCaseImpl
import com.codandotv.streamplayerapp.feature_list_streams.list.domain.GetTopRatedStream
import com.codandotv.streamplayerapp.feature_list_streams.list.domain.GetTopRatedStreamImpl
import com.codandotv.streamplayerapp.feature_list_streams.list.domain.ListStreamAnalytics
import com.codandotv.streamplayerapp.feature_list_streams.list.domain.ListStreamAnalyticsImpl
import com.codandotv.streamplayerapp.feature_list_streams.list.domain.ListStreamUseCase
import com.codandotv.streamplayerapp.feature_list_streams.list.domain.ListStreamUseCaseImpl
import com.codandotv.streamplayerapp.feature_list_streams.list.presentation.screens.ListStreamViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object ListStreamModule {
    val module = module {
        viewModel {
            ListStreamViewModel(
                listStreams = get(),
                listGenres = get(),
                latestStream = get()
            )
        }

        factory<ListStreamUseCase> {
            ListStreamUseCaseImpl(
                repository = get()
            )
        }

        factory<GetGenresUseCase> {
            GetGenresUseCaseImpl(
                repository = get()
            )
        }

        factory<GetTopRatedStream> {
            GetTopRatedStreamImpl(
                repository = get()
            )
        }

        factory<ListStreamAnalytics> {
            ListStreamAnalyticsImpl()
        }

        factory<ListStreamRepository> {
            ListStreamRepositoryImpl(
                service = get(),
            )
        }

        factory<ListStreamService> {
            ListStreamServiceImpl(
                client = get()
            )
        }
    }
}