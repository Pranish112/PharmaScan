package edu.vt.mobiledev.pharmascan.data

class DrugRepository(private val db: AppDatabase) {

    // Drug-related methods
    suspend fun getDrugByHash(hash: String): Drug? {
        return db.drugDao().getDrugByHash(hash)
    }

    // Report-related methods
    suspend fun insertReport(report: Report) {
        db.reportDao().insertReport(report)
    }

    suspend fun getAllReports(): List<Report> {
        return db.reportDao().getAllReports()
    }

    suspend fun getAllCounterfeitReports(): List<Report> {
        return db.reportDao().getAllCounterfeitReports()
    }

    suspend fun deleteReport(report: Report) {
        db.reportDao().deleteReport(report)
    }

    suspend fun insertDrug(drug: Drug) {
        db.drugDao().insertDrug(drug)
    }
}
