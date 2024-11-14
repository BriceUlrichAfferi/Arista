package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.dao.ExerciseDtoDao
import com.openclassrooms.arista.dao.mapper.ExerciseMapper
import com.openclassrooms.arista.data.FakeApiService
import com.openclassrooms.arista.domain.model.Exercise
import kotlinx.coroutines.flow.first

class ExerciseRepository(private val exerciseDao: ExerciseDtoDao) {


    // Get all exercises
    suspend fun getAllExercises(): List<Exercise> {
        return exerciseDao.getAllExercises()
            .first()
            .map { ExerciseMapper.fromDto(it) } // Use the mapper here
    }

    // Add a new exercise
    suspend fun addExercise(exercise: Exercise) {
        ExerciseMapper.toDto(exercise)?.let { exerciseDao.insertExercise(it) } // Use the mapper here
    }

    // Delete an exercise
    suspend fun deleteExercise(exercise: Exercise) {
        exercise.id?.let {
            exerciseDao.deleteExerciseById(id = it)
        }
    }

}