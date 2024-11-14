package com.openclassrooms.arista.domain.model

data class Exercise(
    val id: Long? = null,
    var startTime: Long,
    var duration: Int,
    var category: String,
    var intensity: Int
)