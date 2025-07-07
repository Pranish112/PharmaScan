package edu.vt.mobiledev.pharmascan.data

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import edu.vt.mobiledev.pharmascan.R
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.IOException

class ScanFragment : Fragment() {

    // location provider
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // viewmodel for accessing DB and logic
    private val viewModel: DrugViewModel by activityViewModels { DrugViewModelFactory(requireActivity().application) }

    // File and URI for captured image
    private var photoUri: Uri? = null
    private var photoFile: File? = null

    // Camera launcher to capture the image
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            photoFile?.let { file ->
                val bitmap = ImageUtils.getBitmapFromFile(file)
                bitmap?.let {
                    val hash = ImageUtils.generateHash(it)

                    // Verifies the drug in database by image hash
                    viewModel.verifyDrug(hash) { drug ->
                        lifecycleScope.launch {
                            val location = getLastKnownLocation()
                            if (location == null) {
                                Toast.makeText(requireContext(), "Unable to get location", Toast.LENGTH_SHORT).show()
                                return@launch
                            }

                            // Save report to DB
                            val report = Report(
                                drugName = drug?.name ?: "Unknown",
                                isCounterfeit = drug == null,
                                timestamp = System.currentTimeMillis(),
                                latitude = location.latitude,
                                longitude = location.longitude
                            )
                            viewModel.insertReport(report)

                            // Shows the result dialog
                            if (drug != null) {
                                ResultDialogFragment.newInstance(true, drug.name)
                                    .show(parentFragmentManager, "result")
                            } else {
                                ResultDialogFragment.newInstance(false, null)
                                    .show(parentFragmentManager, "result")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // Launches the camera on button click
        view.findViewById<Button>(R.id.scan_button).setOnClickListener {
            val permissionsNeeded = mutableListOf<String>()
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(Manifest.permission.CAMERA)
            }
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            if (permissionsNeeded.isNotEmpty()) {
                requestPermissions(permissionsNeeded.toTypedArray(), REQUEST_PERMISSIONS)
            } else {
                launchCamera()
            }
        }
    }

    // Launch camera intent
    private fun launchCamera() {
        try {
            val photoFile = createImageFile()
            this.photoFile = photoFile
            photoUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                photoFile
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
            cameraLauncher.launch(intent)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Failed to launch camera", Toast.LENGTH_SHORT).show()
        }
    }

    // Gets the current location using FusedLocationProvider
    private suspend fun getLastKnownLocation(): Location? {
        return if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).await()
        } else null
    }

    // Create temp image file for captured photo
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val fileName = "IMG_${System.currentTimeMillis()}"
        val storageDir = requireContext().externalCacheDir
        return File.createTempFile(fileName, ".jpg", storageDir)
    }

    // Handle permission result - to allow the location when using the app
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS &&
            grantResults.isNotEmpty() &&
            grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        ) {
            launchCamera()
        } else {
            Toast.makeText(requireContext(), "Camera and location permissions are required", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val REQUEST_PERMISSIONS = 1001
    }
}
