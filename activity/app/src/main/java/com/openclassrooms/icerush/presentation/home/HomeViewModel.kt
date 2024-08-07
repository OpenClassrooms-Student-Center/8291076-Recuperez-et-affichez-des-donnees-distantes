package com.openclassrooms.icerush.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.icerush.data.repository.SnowRepository
import com.openclassrooms.icerush.domain.model.SnowReportModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.openclassrooms.icerush.data.repository.Result

@HiltViewModel
class HomeViewModel @Inject constructor(private val dataRepository: SnowRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()



    fun getForecastData(latitude : Double, longitude : Double) {
        dataRepository.fetchForecastData(latitude, longitude).onEach { forecastUpdate ->
            when (forecastUpdate) {
                is Result.Failure -> _uiState.update { currentState ->
                    currentState.copy(
                        isViewLoading = false,
                        errorMessage = forecastUpdate.message
                    )
                }

                Result.Loading -> _uiState.update { currentState ->
                    currentState.copy(
                        isViewLoading = true,
                        errorMessage = null,
                    )
                }

                is Result.Success -> _uiState.update { currentState ->
                    currentState.copy(
                        forecast = forecastUpdate.value,
                        isViewLoading = false,
                        errorMessage = null,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class HomeUiState(
    val forecast: List<SnowReportModel> = emptyList(),
    val isViewLoading: Boolean = false,
    val errorMessage: String? = null
)