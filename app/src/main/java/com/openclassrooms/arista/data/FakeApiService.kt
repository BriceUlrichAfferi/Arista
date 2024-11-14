package com.openclassrooms.arista.data

import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.model.Sleep
import com.openclassrooms.arista.domain.model.User
import com.openclassrooms.arista.toEpochMilli
import java.time.LocalDateTime

class FakeApiService {

    // Static data
    var user: User = User("John Doe", "johndoe@example.com")

    private val sleepData = listOf(
        Sleep(LocalDateTime.now().minusDays(1).toEpochMilli(), 7, 8),
        Sleep(LocalDateTime.now().minusDays(2).toEpochMilli(), 6, 5),
        Sleep(LocalDateTime.now().minusDays(3).toEpochMilli(), 8, 9)
    )




    private val exerciseData = mutableListOf(
        Exercise(1, LocalDateTime.now().minusHours(5).toEpochMilli(), 30,
            ExerciseCategory.Running.toString(), 7),
        Exercise(2, LocalDateTime.now().minusDays(1).minusHours(3).toEpochMilli(), 45,
            ExerciseCategory.Swimming.toString(), 6),
        Exercise(3, LocalDateTime.now().minusDays(2).minusHours(4).toEpochMilli(), 60,
            ExerciseCategory.Football.toString(), 8)
    )




    // CRUD for Sleep
    fun getAllSleeps() = sleepData.toList()



    fun findSleepByStartTime(startTime: LocalDateTime): Sleep? {
        // Convert startTime to Long before comparing
        val startTimeInMillis = startTime.toEpochMilli()
        return sleepData.find { it.startTime == startTimeInMillis }
    }


    // CRUD for Exercise
    fun getAllExercises() = exerciseData.toList()

    fun addExercise(exercise: Exercise) {
        exerciseData.add(exercise)
    }

    fun deleteExercise(exercise: Exercise) {
        exerciseData.remove(exercise)
    }
}
