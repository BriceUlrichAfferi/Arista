package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

@RunWith(JUnit4::class)
class GetAllExercisesUseCaseTest {


    @Mock
    private lateinit var exerciseRepository: ExerciseRepository


    private lateinit var getAllExercisesUseCase: GetAllExercisesUseCase


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getAllExercisesUseCase = GetAllExercisesUseCase(exerciseRepository)
    }




    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }




    @Test
    fun `when repository returns exercises, use case should return them`(): Unit = runBlocking {
        // Arrange
        val fakeExercises = listOf(
            Exercise(
                startTime = LocalDateTime.now().toEpochMilli(),
                duration = 30,
                category = ExerciseCategory.Running.toString(),
                intensity = 5
            ),
            Exercise(
                startTime = LocalDateTime.now().plusHours(1).toEpochMilli(),
                duration = 45,
                category = ExerciseCategory.Riding.toString(),
                intensity = 7
            )
        )
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(fakeExercises)


        // Act
        val result = getAllExercisesUseCase.execute()


        // Assert
        assertEquals(fakeExercises, result)
    }


    @Test
    fun `when repository returns empty list, use case should return empty list`(): Unit = runBlocking {
        // Arrange
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(emptyList())


        // Act
        val result = getAllExercisesUseCase.execute()


        // Assert
        assertTrue(result.isEmpty())
    }


}