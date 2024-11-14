package com.openclassrooms.arista.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.data.entity.SleepDto
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Dao
interface SleepDtoDao {


    @Insert
    suspend fun insertSleep(sleep:SleepDto): Long


    @Query("SELECT * FROM sleep")
    fun getAllSleep(): Flow<List<SleepDto>>


    @Query("DELETE FROM sleep WHERE start_time = :startTime")
    suspend fun deleteSleepByStartTime(startTime: kotlin.Long)
}