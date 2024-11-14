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

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): Flow<UserDto?>

    @Query("DELETE FROM user WHERE email = :email")
    suspend fun deleteUserByEmail(email: String)
}

