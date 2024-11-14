package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.Sleep
import javax.inject.Inject

class DeleteSleepUseCase @Inject constructor(private val sleepRepository: SleepRepository) {
    suspend fun execute(sleep: Sleep) {
        sleepRepository.deleteSleep(sleep)
    }
}