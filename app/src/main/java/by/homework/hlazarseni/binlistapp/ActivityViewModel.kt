package by.homework.hlazarseni.binlistapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.homework.hlazarseni.binlistapp.repository.Repository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch



class ActivityViewModel(
    private val repository: Repository
    ) : ViewModel() {

    val databaseFlow = flow<List<String>> {
        runCatching {repository.getNumbersDB() }
            .onSuccess { emit(it) }
            .onFailure { emit(emptyList()) }
    }.shareIn(
        viewModelScope,
        SharingStarted.Eagerly,
       replay = 1
    )

    fun insertNumberDB(numberCard: String) {
        viewModelScope.launch {
            repository.insertNumberDB(numberCard)
        }
    }

}