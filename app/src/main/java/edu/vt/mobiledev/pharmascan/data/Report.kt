package edu.vt.mobiledev.pharmascan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reports")
data class Report(
    @PrimaryKey(autoGenerate = true)
    val reportId: Int = 0,      // Unique Report ID
    val drugName: String,       // Name of the drug being reported
    val isCounterfeit: Boolean, // If it is counterfeit or not
    val timestamp: Long,        // Time when the report was made
    val latitude: Double,       // The latitude coordinates
    val longitude: Double,      // The longitude coordinates
)
