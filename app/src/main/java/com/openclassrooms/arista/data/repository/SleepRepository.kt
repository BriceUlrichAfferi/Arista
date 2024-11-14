package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.dao.SleepDtoDao
import com.openclassrooms.arista.dao.mapper.SleepMapper
import com.openclassrooms.arista.domain.model.Sleep
import kotlinx.coroutines.flow.first

class SleepRepository(private val sleepDao: SleepDtoDao) {


    // Get all sleep records
    suspend fun getAllSleep(): List<Sleep> {
        return sleepDao.getAllSleep()
            .first()
            .map { SleepMapper.fromDto(it) } // Use the mapper here
    }

    // Add a new sleep
    suspend fun addSleep(startTime: Long, duration: Int, quality: Int) {
        val sleep = Sleep(startTime, duration, quality) // Create the Sleep object
        SleepMapper.toDto(sleep)?.let { sleepDao.insertSleep(it) }
    }


    // Delete sleep
    suspend fun deleteSleep(sleep: Sleep) {
        sleepDao.deleteSleepByStartTime(startTime = sleep.startTime)
    }

    }
