package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.model.Sleep
import com.openclassrooms.arista.domain.usecase.AddNewSleepUseCase
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

class AddNewSleepUseCaseTest {

    @Mock
    private lateinit var sleepRepository: SleepRepository

    private lateinit var addNewSleepUseCase: AddNewSleepUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        addNewSleepUseCase = AddNewSleepUseCase(sleepRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }

    @Test
    fun `when addSleep is called, it should add the sleep to the repository`() = runBlocking {
        // Arrange
        val startTime = LocalDateTime.now().toEpochMilli()
        val duration = 30
        val quality = 7
        val sleepToAdd = Sleep(startTime, duration, quality)

        // Act
        addNewSleepUseCase.execute(startTime, duration, quality)

        // Assert
        Mockito.verify(sleepRepository).addSleep(startTime, duration, quality)
    }

    @Test
    fun `when getAllSleep is called after adding sleep, it should return the added sleep`() = runBlocking {
        // Arrange
        val startTime = LocalDateTime.now().toEpochMilli()
        val duration = 30
        val quality = 7
        val sleepToAdd = Sleep(startTime, duration, quality)
        val newSleeps = listOf(sleepToAdd)

        // Mock initial state
        Mockito.`when`(sleepRepository.getAllSleep()).thenReturn(emptyList())
        // Mock state after adding sleep
        Mockito.`when`(sleepRepository.getAllSleep()).thenReturn(newSleeps)

        // Act
        addNewSleepUseCase.execute(startTime, duration, quality)
        val result = sleepRepository.getAllSleep()

        // Assert
        TestCase.assertEquals(newSleeps, result)
    }
}
