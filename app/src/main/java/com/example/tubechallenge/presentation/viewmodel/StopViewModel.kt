package com.example.tubechallenge.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tubechallenge.domain.model.Stop
import com.example.tubechallenge.domain.repository.StopRepository
import com.example.tubechallenge.presentation.state.StopState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StopViewModel @Inject constructor(
    private val stopRepository: StopRepository
): ViewModel() {

    private val _stopState = mutableStateOf(StopState())
    val stopState: State<StopState> = _stopState


    var stopStationNameState by mutableStateOf("")
    var stopTimeArrivedState by mutableStateOf("")
    var stopTimeDepartedState by mutableStateOf("")
    var stopNotesState by mutableStateOf("")

    lateinit var getAllStops: Flow<List<Stop>>

    init {
        viewModelScope.launch {
            getAllStops = stopRepository.getAllStops()
        }
    }

    fun onStationNameChanged(newStationName: String) {
        stopStationNameState = newStationName
    }

    fun onTimeArrivedChanged(newTime: String) {
        stopTimeArrivedState = newTime
    }

    fun onTimeDepartedChanged(newTime: String) {
        stopTimeDepartedState = newTime
    }

    fun onNotesChanged(newNote: String) {
        stopNotesState = newNote
    }

    fun addStop(stop: Stop) {
        viewModelScope.launch(Dispatchers.IO) {
            stopRepository.addStop(stop)
        }
    }

    fun getStopById(id: Long) : Flow<Stop> {
        return stopRepository.getStopById(id)
    }

    fun updateStop(stop: Stop) {
        viewModelScope.launch(Dispatchers.IO) {
            stopRepository.updateStop(stop)
        }
    }

    fun deleteStop(stop: Stop) {
        viewModelScope.launch(Dispatchers.IO) {
            stopRepository.deleteStop(stop)
        }
    }
}
