package com.drivenext.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

/**
 * Модель автомобиля в доменном слое
 */
@Parcelize
data class Car(
    val id: String,
    val brand: String,
    val model: String,
    val year: Int,
    val color: String,
    val licensePlate: String,
    val fuelType: FuelType,
    val transmission: TransmissionType,
    val seatingCapacity: Int,
    val pricePerDay: BigDecimal,
    val imageUrls: List<String>,
    val features: List<String>,
    val location: CarLocation,
    val isAvailable: Boolean = true,
    val rating: Float = 0f,
    val reviewsCount: Int = 0
) : Parcelable

@Parcelize
data class CarLocation(
    val address: String,
    val latitude: Double,
    val longitude: Double
) : Parcelable

enum class FuelType {
    GASOLINE, DIESEL, ELECTRIC, HYBRID
}

enum class TransmissionType {
    MANUAL, AUTOMATIC
}
