package edu.vt.mobiledev.pharmascan.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// The Room database holding the drug and report entities
@Database(entities = [Drug::class, Report::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    // access objects for drug and report
    abstract fun drugDao(): DrugDao
    abstract fun reportDao(): ReportDao

    companion object {
        // singleton instance so that we avoid multiple DB instances
        @Volatile private var INSTANCE: AppDatabase? = null

        // Builds or returns to the existing db if created
        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "drug_auth_db" // the database file
                ).build().also { INSTANCE = it }
            }


    }
}