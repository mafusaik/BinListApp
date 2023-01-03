package by.homework.hlazarseni.binlistapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.homework.hlazarseni.binlistapp.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class ActivityViewModel(
    private val repository: Repository
) : ViewModel() {

    val databaseFlow = flow<List<String>> {
        while (true) {
            repository.getNumbersDB()
                .onSuccess { emit(it) }
                .onFailure { emit(emptyList()) }
            delay(5000)
        }

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