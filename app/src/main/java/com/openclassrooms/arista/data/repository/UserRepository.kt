package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.dao.UserDtoDao
import com.openclassrooms.arista.dao.mapper.UserMapper
import com.openclassrooms.arista.data.FakeApiService
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDao: UserDtoDao
) {

    // Get the current user from the database
    suspend fun getUser(): User? {
        return userDao.getUser()
            .first() // Get the first (and only) emission from the Flow
            ?.let { UserMapper.fromDto(it) } // Map UserDto to User, if it exists
    }

    // Set or update the current user in the database and API service
    suspend fun setUser(user: User) {
        val userDto = UserMapper.toDto(user)
        userDao.insertUser(userDto)
    }

    // Delete the current user based on their email
    suspend fun deleteUser(user: User) {
        userDao.deleteUserByEmail(user.email)
    }
}
