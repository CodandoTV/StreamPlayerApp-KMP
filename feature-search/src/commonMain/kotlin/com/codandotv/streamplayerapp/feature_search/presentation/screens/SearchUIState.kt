package com.codandotv.streamplayerapp.feature_search.presentation.screens

import com.codandotv.streamplayerapp.feature_search.data.model.ListSearchStreamResponse

sealed class SearchUIState {
    data class Success(val listCharacters: ListSearchStreamResponse) : SearchUIState()
    data class Error(val messageError: String = String()) : SearchUIState()
    object Loading : SearchUIState()
    object Empty : SearchUIState()
}
