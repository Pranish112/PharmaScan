package edu.vt.mobiledev.pharmascan.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.heatmaps.HeatmapTileProvider
import edu.vt.mobiledev.pharmascan.R
import edu.vt.mobiledev.pharmascan.data.DrugViewModel
import edu.vt.mobiledev.pharmascan.data.DrugViewModelFactory

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private val viewModel: DrugViewModel by activityViewModels {
        DrugViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map_fragment_container) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Move camera to default zoomed out view
        val defaultLocation = LatLng(0.0, 0.0)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 2f))

        viewModel.allReports.observe(viewLifecycleOwner) { reports ->
            map.clear()

            // Add markers for each report
            for (report in reports) {
                val location = LatLng(report.latitude, report.longitude)
                val markerTitle = if (report.isCounterfeit) "Counterfeit" else "Authentic"

                val markerColor = if (report.isCounterfeit) {
                    BitmapDescriptorFactory.HUE_RED
                } else {
                    BitmapDescriptorFactory.HUE_GREEN
                }

                map.addMarker(
                    MarkerOptions()
                        .position(location)
                        .title(markerTitle)
                        .icon(BitmapDescriptorFactory.defaultMarker(markerColor))
                )
            }

            // Create heatmap from all report locations
            val latLngs = reports.map { LatLng(it.latitude, it.longitude) }
            if (latLngs.isNotEmpty()) {
                val heatmapProvider = HeatmapTileProvider.Builder()
                    .data(latLngs)
                    .build()
                map.addTileOverlay(TileOverlayOptions().tileProvider(heatmapProvider))
            }

            // Move camera to the most recent report if available
            if (reports.isNotEmpty()) {
                val latest = reports.last()
                val latestLocation = LatLng(latest.latitude, latest.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latestLocation, 10f))
            }
        }
    }
}
