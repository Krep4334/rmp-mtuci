package com.drivenext.app.data.remote.dto

import com.drivenext.app.domain.model.Gender
import com.drivenext.app.domain.model.User
import java.text.SimpleDateFormat
import java.util.*

/**
 * DTO для пользователя из Supabase
 * Временно без @Serializable до подключения kotlinx.serialization
 */
data class UserDto(
    val id: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val middle_name: String? = null,
    val birth_date: String,
    val gender: String,
    val profile_photo_url: String? = null,
    val driver_license_number: String? = null,
    val driver_license_issue_date: String? = null,
    val driver_license_photo_url: String? = null,
    val passport_photo_url: String? = null,
    val is_verified: Boolean = false,
    val created_at: String,
    val updated_at: String
)

/**
 * Расширение для конвертации DTO в доменную модель
 */
fun UserDto.toDomain(): User {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    
    return User(
        id = id,
        email = email,
        firstName = first_name,
        lastName = last_name,
        middleName = middle_name,
        birthDate = dateFormat.parse(birth_date) ?: Date(),
        gender = Gender.valueOf(gender.uppercase()),
        profilePhotoUrl = profile_photo_url,
        driverLicenseNumber = driver_license_number,
        driverLicenseIssueDate = driver_license_issue_date?.let { dateFormat.parse(it) },
        driverLicensePhotoUrl = driver_license_photo_url,
        passportPhotoUrl = passport_photo_url,
        isVerified = is_verified,
        createdAt = dateFormat.parse(created_at) ?: Date(),
        updatedAt = dateFormat.parse(updated_at) ?: Date()
    )
}
