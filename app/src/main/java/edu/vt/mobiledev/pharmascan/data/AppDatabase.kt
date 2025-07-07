package edu.vt.mobiledev.pharmascan.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Drug::class, Report::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun drugDao(): DrugDao
    abstract fun reportDao(): ReportDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "drug_auth_db"
                ).build().also { INSTANCE = it }
            }


    }
}