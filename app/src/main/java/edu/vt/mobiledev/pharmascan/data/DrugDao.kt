package edu.vt.mobiledev.pharmascan.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DrugDao {
    @Query("SELECT * FROM drugs WHERE imageHash = :hash")
    suspend fun findDrugByHash(hash: String): Drug?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrug(drug: Drug)

    @Insert
    suspend fun insertReport(report: Report)

    @Query("SELECT * FROM reports")
    fun getAllReports(): LiveData<List<Report>>

    @Query("SELECT * FROM drugs WHERE imageHash = :hash")
    suspend fun getDrugByHash(hash: String): Drug?
}