package com.drivenext.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.Date

/**
 * Модель бронирования в доменном слое
 */
@Parcelize
data class Booking(
    val id: String,
    val userId: String,
    val carId: String,
    val startDate: Date,
    val endDate: Date,
    val totalPrice: BigDecimal,
    val status: BookingStatus,
    val pickupLocation: String,
    val dropoffLocation: String,
    val notes: String?,
    val createdAt: Date,
    val updatedAt: Date
) : Parcelable

enum class BookingStatus {
    PENDING, CONFIRMED, ACTIVE, COMPLETED, CANCELLED
}
