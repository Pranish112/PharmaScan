package edu.vt.mobiledev.pharmascan.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

// Data Access Object (DAO) for accessing the reports tables
@Dao
interface ReportDao {

    @Insert
    suspend fun insertReport(report: Report)

    @Query("SELECT * FROM reports")
    suspend fun getAllReports(): List<Report>

    @Query("SELECT * FROM reports WHERE isCounterfeit = 1")
    suspend fun getAllCounterfeitReports(): List<Report>

    @Query("SELECT * FROM reports")
    fun getAllReportsLive(): LiveData<List<Report>>

    @Delete
    suspend fun deleteReport(report: Report)
}
