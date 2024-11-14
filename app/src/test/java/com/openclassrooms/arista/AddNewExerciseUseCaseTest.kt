package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.AddNewExerciseUseCase
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

class AddNewExerciseUseCaseTest {

    @Mock
    private lateinit var exerciseRepository: ExerciseRepository


    private lateinit var addNewExerciseUseCase: AddNewExerciseUseCase


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        addNewExerciseUseCase = AddNewExerciseUseCase(exerciseRepository)
    }




    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }




    @Test
    fun `when addExercise is called, it should add the exercise to the repository`() = runBlocking {
        // Arrange
        val exerciseToAdd = Exercise(
            id = 1,  // assuming id is non-null
            startTime = LocalDateTime.now().toEpochMilli(),
            duration = 30,
            category = ExerciseCategory.Running.toString(),
            intensity = 5
        )

        // Act
        addNewExerciseUseCase.execute(exerciseToAdd)

        // Assert
        Mockito.verify(exerciseRepository).addExercise(exerciseToAdd)
    }


    @Test
    fun `when getAllExercises is called after deletion, it should add new exercises`() = runBlocking {
        // Arrange
        val exerciseToAdd = Exercise(
            id = 1,
            startTime = LocalDateTime.now().toEpochMilli(),
            duration = 30,
            category = ExerciseCategory.Running.toString(),
            intensity = 5
        )
        val newExercises = listOf(
            Exercise(
                id = 2,
                startTime = LocalDateTime.now().plusHours(1).toEpochMilli(),
                duration = 45,
                category = ExerciseCategory.Riding.toString(),
                intensity = 7
            )
        )

        // Mock initial empty state
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(emptyList())

        // Simulate the state after exercise is added
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(newExercises)

        // Act
        addNewExerciseUseCase.execute(exerciseToAdd)
        val result = exerciseRepository.getAllExercises()

        // Assert
        TestCase.assertEquals(newExercises, result)
    }



}