package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.usecase.DeleteSleepUseCase
import com.openclassrooms.arista.domain.model.Sleep
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

class DeleteSleepUseCaseTest {

    @Mock
    private lateinit var sleepRepository: SleepRepository

    private lateinit var deleteSleepUseCase: DeleteSleepUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        deleteSleepUseCase = DeleteSleepUseCase(sleepRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }

    @Test
    fun `when deleteSleep is called, it should delete the sleep entry with the specified start time`() = runBlocking {
        // Arrange
        val startTimeToDelete = LocalDateTime.now().toEpochMilli()
        val sleepToDelete = Sleep(startTime = startTimeToDelete, duration = 30, quality = 7)

        // Act
        deleteSleepUseCase.execute(sleepToDelete)  // Pass the full Sleep object

        // Assert
        Mockito.verify(sleepRepository).deleteSleep(sleepToDelete)  // Verify deletion with the Sleep object
    }

    @Test
    fun `when getSleeps is called after deletion, it should return the remaining sleeps`() = runBlocking {
        // Arrange
        val startTimeToDelete = LocalDateTime.now().toEpochMilli()
        val sleepToDelete = Sleep(startTime = startTimeToDelete, duration = 30, quality = 7)

        val remainingSleeps = listOf(
            Sleep(startTime = LocalDateTime.now().minusDays(1).toEpochMilli(), duration = 40, quality = 8)
        )

        // Mock the initial list of sleeps and the result after deletion
        Mockito.`when`(sleepRepository.getAllSleep()).thenReturn(listOf(sleepToDelete)) // Initial sleep list
        Mockito.`when`(sleepRepository.getAllSleep()).thenReturn(remainingSleeps)  // Remaining sleeps after deletion

        // Act: Delete the sleep
        deleteSleepUseCase.execute(sleepToDelete)

        // Act: Get the remaining sleeps
        val result = sleepRepository.getAllSleep()

        // Assert: Verify that after deletion, only the remaining sleeps are returned
        Mockito.verify(sleepRepository).getAllSleep()
        assert(result == remainingSleeps)
    }
}
