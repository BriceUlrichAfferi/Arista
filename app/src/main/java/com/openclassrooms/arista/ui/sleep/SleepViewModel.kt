package com.openclassrooms.arista.ui.sleep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.arista.domain.model.Sleep
import com.openclassrooms.arista.domain.usecase.AddNewSleepUseCase
import com.openclassrooms.arista.domain.usecase.DeleteSleepUseCase
import com.openclassrooms.arista.domain.usecase.GetAllSleepsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SleepViewModel @Inject constructor(
    private val getAllSleepsUseCase: GetAllSleepsUseCase,
    private val addNewSleepUseCase: AddNewSleepUseCase,
    private val deleteSleepUseCase: DeleteSleepUseCase
) :
    ViewModel() {
    private val _sleeps = MutableStateFlow<List<Sleep>>(emptyList())
    val sleeps: StateFlow<List<Sleep>> = _sleeps.asStateFlow()

    fun fetchSleeps() {
            viewModelScope.launch(Dispatchers.IO) {
                val sleep = getAllSleepsUseCase.execute()
                _sleeps.value = sleep
            }

    }

    fun deleteSleep(sleep: Sleep) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteSleepUseCase.execute(sleep)
            fetchSleeps()
        }
    }

    fun addSleep(sleep: Long, duration: Int, quality: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            addNewSleepUseCase.execute(sleep, duration, quality)
            fetchSleeps()
        }
    }

}
