package com.openclassrooms.arista.dao.mapper

import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.domain.model.Sleep

object SleepMapper {

    // Convert SleepDto to Sleep
    fun fromDto(dto: SleepDto): Sleep{
        return Sleep(
            startTime = dto.startTime,
            duration = dto.duration,
            quality = dto.quality
        )
    }

      // Convert Sleep to SleepDto
    fun toDto(sleep: Sleep): SleepDto {  // Corrected this line to accept Sleep object
        return SleepDto(
            startTime = sleep.startTime,
            duration = sleep.duration,
            quality = sleep.quality
        )
    }
}
