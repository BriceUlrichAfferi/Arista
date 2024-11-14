package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.SleepRepository
import javax.inject.Inject

class AddNewSleepUseCase @Inject constructor(private val sleepRepository: SleepRepository) {
    suspend fun execute(startTime: Long, duration: Int, quality: Int) {
        // Pass all three parameters to the addSleep method in SleepRepository
        sleepRepository.addSleep(startTime, duration, quality)
    }
}
