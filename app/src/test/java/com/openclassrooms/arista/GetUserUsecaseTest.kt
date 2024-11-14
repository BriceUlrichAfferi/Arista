package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.model.User
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import com.openclassrooms.arista.domain.usecase.GetUserUsecase
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime

class GetUserUsecaseTest {

    @Mock
    private lateinit var userRepository: UserRepository


    private lateinit var getUserUsecase: GetUserUsecase


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getUserUsecase = GetUserUsecase(userRepository)
    }




    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }




    @Test
    fun `when repository returns exercises, use case should return them`(): Unit = runBlocking {
        // Arrange
        val fakeUser =
            User(
                name = "Jody",
                email = "jodychoco@yahoo.com"
            )
        Mockito.`when`(userRepository.getUser()).thenReturn(fakeUser)


        // Act
        val result = getUserUsecase.execute()


        // Assert
        TestCase.assertEquals(fakeUser, result)
    }


    @Test
    fun `when repository returns empty user, use case should return empty user`() = runBlocking {
        // Arrange
        val emptyUser = User(name = "Zagol", email = "zagol@gmail.com")  // Providing default empty values for name and email
        Mockito.`when`(userRepository.getUser()).thenReturn(emptyUser)  // Return empty user

        // Act
        val result = getUserUsecase.execute()  // Execute use case

        // Assert
        TestCase.assertEquals(emptyUser, result)  // Assert that the result is the empty user
    }




}