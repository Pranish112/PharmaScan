package edu.vt.mobiledev.pharmascan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drugs")
data class Drug(
    @PrimaryKey val id: String,
    val name: String,
    val imageHash: String
)


