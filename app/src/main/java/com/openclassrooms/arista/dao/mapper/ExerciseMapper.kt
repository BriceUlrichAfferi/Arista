package com.openclassrooms.arista.dao.mapper

import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.domain.model.Exercise

object ExerciseMapper {
    // Convert ExerciseDto to Exercise
    fun fromDto(dto: ExerciseDto): Exercise {
        return Exercise(
            id = dto.id,
            startTime = dto.startTime,
            duration = dto.duration,
            category = dto.category,
            intensity = dto.intensity
        )
    }

    // Convert Exercise to ExerciseDto
    fun toDto(exercise: Exercise): ExerciseDto? {
        return exercise.id?.let {
            ExerciseDto(
                id = it,
                startTime = exercise.startTime,
                duration = exercise.duration,
                category = exercise.category.toString(),
                intensity = exercise.intensity
            )
        }
    }
}