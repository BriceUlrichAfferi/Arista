package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.SleepRepository
import javax.inject.Inject

class AddNewSleepUseCase @Inject constructor(private val sleepRepository: SleepRepository) {
    suspend fun execute(startTime: Long, duration: Int, quality: Int) {
        sleepRepository.addSleep(startTime, duration, quality)
    }
}
