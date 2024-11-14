package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.model.Sleep
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import com.openclassrooms.arista.domain.usecase.GetAllSleepsUseCase
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

class GetAllSleepsUseCaseTest {

    @Mock
    private lateinit var sleepRepository: SleepRepository


    private lateinit var getAllSleepsUseCase: GetAllSleepsUseCase


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getAllSleepsUseCase = GetAllSleepsUseCase(sleepRepository)
    }




    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }




    @Test
    fun `when repository returns sleeps, use case should return them`(): Unit = runBlocking {
        // Arrange
        val fakeSleeps = listOf(
            Sleep(
                startTime = LocalDateTime.now().toEpochMilli(),
                duration = 30,
                quality = 5
            ),
            Sleep(
                startTime = LocalDateTime.now().toEpochMilli(),
                duration = 120,
                quality = 8
            )
        )
        Mockito.`when`(sleepRepository.getAllSleep()).thenReturn(fakeSleeps)


        // Act
        val result = getAllSleepsUseCase.execute()


        // Assert
        TestCase.assertEquals(fakeSleeps, result)
    }


    @Test
    fun `when repository returns empty list, use case should return empty list`(): Unit = runBlocking {
        // Arrange
        Mockito.`when`(sleepRepository.getAllSleep()).thenReturn(emptyList())


        // Act
        val result = getAllSleepsUseCase.execute()


        // Assert
        TestCase.assertTrue(result.isEmpty())
    }

}