package com.openclassrooms.arista.dao.mapper

import com.openclassrooms.arista.data.entity.UserDto
import com.openclassrooms.arista.domain.model.User

object UserMapper {
    // Convert UserDto to User
    fun fromDto(dto: UserDto): User {
        return User(
            name = dto.name, // Corrected to use name
            email = dto.email // Corrected to use email as is
        )
    }

    // Convert User to UserDto
    fun toDto(user: User): UserDto {
        return UserDto(
            name = user.name, // Corrected to use name
            email = user.email // Corrected to use email
        )
    }
}
