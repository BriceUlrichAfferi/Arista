package com.openclassrooms.arista

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

// Extension function to convert Long to LocalDateTime
fun Long.toLocalDateTime(): LocalDateTime =
    Instant.ofEpochMilli(this).atZone(ZoneOffset.UTC).toLocalDateTime()

// Extension function to convert LocalDateTime to Long
fun LocalDateTime.toEpochMilli(): Long =
    this.toInstant(ZoneOffset.UTC).toEpochMilli()
