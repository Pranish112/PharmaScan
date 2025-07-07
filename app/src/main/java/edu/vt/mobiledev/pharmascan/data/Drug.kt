package edu.vt.mobiledev.pharmascan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// represents a drug record in the database
@Entity(tableName = "drugs")
data class Drug(
    @PrimaryKey val id: String, // Unique ID for each drug
    val name: String,           // The name of the drug
    val imageHash: String       // hash of the scanned image
)


