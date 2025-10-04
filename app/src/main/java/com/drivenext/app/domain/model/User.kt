package com.drivenext.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

/**
 * Модель пользователя в доменном слое
 */
@Parcelize
data class User(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val birthDate: Date,
    val gender: Gender,
    val profilePhotoUrl: String?,
    val driverLicenseNumber: String?,
    val driverLicenseIssueDate: Date?,
    val driverLicensePhotoUrl: String?,
    val passportPhotoUrl: String?,
    val isVerified: Boolean = false,
    val createdAt: Date,
    val updatedAt: Date
) : Parcelable

enum class Gender {
    MALE, FEMALE, OTHER
}
