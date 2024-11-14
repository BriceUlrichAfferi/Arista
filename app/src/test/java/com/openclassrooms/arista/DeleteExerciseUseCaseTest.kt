package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.DeleteExerciseUseCase
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

class DeleteExerciseUseCaseTest {

    @Mock
    private lateinit var exerciseRepository: ExerciseRepository

    private lateinit var deleteExerciseUseCase: DeleteExerciseUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        deleteExerciseUseCase = DeleteExerciseUseCase(exerciseRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }

    @Test
    fun `when deleteExercise is called, it should remove the exercise from repository`() = runBlocking {
        // Arrange
        val exerciseToDelete = Exercise(
            id = 1,  // assuming id is non-null
            startTime = LocalDateTime.now().toEpochMilli(),
            duration = 30,
            category = ExerciseCategory.Running.toString(),
            intensity = 5
        )

        // Act
        deleteExerciseUseCase.execute(exerciseToDelete)  // Ensure exercise is passed here

        // Assert
        Mockito.verify(exerciseRepository).deleteExercise(exerciseToDelete)  // Verify deletion
    }

    @Test
    fun `when getAllExercises is called after deletion, it should return remaining exercises`() = runBlocking {
        // Arrange
        val exerciseToDelete = Exercise(
            id = 1,  // assuming id is non-null
            startTime = LocalDateTime.now().toEpochMilli(),
            duration = 30,
            category = ExerciseCategory.Running.toString(),
            intensity = 5
        )
        val remainingExercises = listOf(
            Exercise(
                id = 2,
                startTime = LocalDateTime.now().plusHours(1).toEpochMilli(),
                duration = 45,
                category = ExerciseCategory.Riding.toString(),
                intensity = 7
            )
        )

        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(listOf(exerciseToDelete, remainingExercises.first()))  // Simulate the state before deletion

        // Act
        deleteExerciseUseCase.execute(exerciseToDelete)  // Delete exercise
        val result = exerciseRepository.getAllExercises()  // Get remaining exercises

        // Assert
        TestCase.assertEquals(remainingExercises, result)  // Ensure the deleted exercise is no longer in the list
    }
}
