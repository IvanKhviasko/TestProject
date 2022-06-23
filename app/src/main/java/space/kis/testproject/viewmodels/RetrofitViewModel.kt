package space.kis.testproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.kis.domain.model.Country
import space.kis.domain.model.LceState
import space.kis.domain.usecase.GetCountryUseCase
import space.kis.domain.usecase.GetFavoriteInsertUseCase

class RetrofitViewModel(
    private val getCountryUseCase: GetCountryUseCase,
    private val getFavoriteInsertUseCase: GetFavoriteInsertUseCase
) : ViewModel() {

    private val _lceFlow = MutableStateFlow<LceState<List<Country>>>(LceState.Loading)
    val lceFlow: Flow<LceState<List<Country>>> = _lceFlow.asStateFlow()

    val _searchQueryFlow = MutableStateFlow("")
    val searchQueryFlow = _searchQueryFlow.asStateFlow()

    init {
        viewModelScope.launch {
            getCountryUseCase.invoke().onSuccess {
                _lceFlow.tryEmit(LceState.Content(country = it))
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            searchQueryFlow.debounce { 100 }
                .mapLatest {
                    filterCountryList(it)
                }
        }

    }

    suspend fun filterCountryList(query: String = ""): List<Country> { ///
        return getCountryUseCase.invoke().fold(
            onSuccess = { it ->
                it.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            },
            onFailure = {
                emptyList()
            }
        )
    }

    fun addCountryToDb(
        id: Long,
        name: String,
        capital: String,
        area: Long,
        population: Long,
        flag: String
    ) = viewModelScope.launch {
        val country = Country(id, name, capital, area, population, flag)
        getFavoriteInsertUseCase.invoke(country)
    }

}