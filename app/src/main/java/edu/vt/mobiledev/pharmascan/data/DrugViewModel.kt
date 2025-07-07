package edu.vt.mobiledev.pharmascan.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DrugViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DrugRepository(AppDatabase.getDatabase(application))

    // LiveData for real-time observations
    val allReports: LiveData<List<Report>> = AppDatabase.getDatabase(application).reportDao().getAllReportsLive()

    // Used in ScanFragment to verify a scanned drug hash
    fun verifyDrug(hash: String, callback: (Drug?) -> Unit) {
        viewModelScope.launch {
            val result = repository.getDrugByHash(hash)
            callback(result)
        }
    }

    fun insertDrug(drug: Drug) {
        viewModelScope.launch {
            repository.insertDrug(drug)
        }
    }

    // Store the report in database
    fun insertReport(report: Report) {
        viewModelScope.launch {
            repository.insertReport(report)
        }
    }

    fun getAllCounterfeitReports(callback: (List<Report>) -> Unit) {
        viewModelScope.launch {
            val reports = repository.getAllCounterfeitReports()
            callback(reports)
        }
    }

    fun deleteReport(report: Report) {
        viewModelScope.launch {
            repository.deleteReport(report)
        }
    }

    suspend fun verifyDrugSuspending(hash: String): Drug? {
        return repository.getDrugByHash(hash)
    }


}

class DrugViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = DrugViewModel(app) as T
}
