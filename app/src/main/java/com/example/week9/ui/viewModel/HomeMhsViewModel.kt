package com.example.week9.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week9.data.entity.Mahasiswa
import com.example.week9.repository.RepositoryMhs
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow

data class  HomeUiState(
    val listMhs: List<Mahasiswa> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)

class HomeMhsViewModel (
    private val repositoryMhs: RepositoryMhs
) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> = repositoryMhs.getAllMhs()
        .filterNotNull()
        .map {
            HomeUiState(
                listMhs = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(
                isLoading = true,
            )
        )
}