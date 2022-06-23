package space.kis.testproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import space.kis.domain.model.Country
import space.kis.domain.model.LceState
import space.kis.domain.usecase.DeleteCountryFromDatabaseUseCase
import space.kis.domain.usecase.GetSubscribeToDatabaseUseCase

class DatabaseViewModel(
    private val getSubscribeToDatabaseUseCase: GetSubscribeToDatabaseUseCase,
    private val deleteCountryFromDatabaseUseCase: DeleteCountryFromDatabaseUseCase
) : ViewModel() {

    private val _lceDatabaseFlow = MutableStateFlow<LceState<Flow<List<Country>>>>(LceState.Loading)
    val lceDatabaseFlow: Flow<LceState<Flow<List<Country>>>> = _lceDatabaseFlow.asStateFlow()

    init {
        viewModelScope.launch {
            getSubscribeToDatabaseUseCase.invoke().fold(
                onSuccess = {
                    _lceDatabaseFlow.tryEmit(LceState.Content(country = it))
                },
                onFailure = { exception ->
                    exception
                }
            )
        }
    }

    fun onCountrySwiped(country: Country) = viewModelScope.launch {
        deleteCountryFromDatabaseUseCase.invoke(country)
    }

}