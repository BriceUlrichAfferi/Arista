package com.openclassrooms.arista.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.entity.UserDto
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDtoDao {

    @Insert
    suspend fun insertUser(user: UserDto): Long

    // Fetch the single user record (returns null if no user exists)
    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): Flow<UserDto?>

    // Delete the user by email
    @Query("DELETE FROM user WHERE email = :email")
    suspend fun deleteUserByEmail(email: String)
}

