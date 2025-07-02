package edu.vt.mobiledev.pharmascan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reports")
data class Report(
    @PrimaryKey(autoGenerate = true) val reportId: Int = 0,
    val drugName: String,
    val isCounterfeit: Boolean,
    val timestamp: Long,
    val latitude: Double,
    val longitude: Double,
)
