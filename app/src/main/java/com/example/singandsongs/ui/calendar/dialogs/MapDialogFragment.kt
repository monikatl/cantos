package com.example.singandsongs.ui.calendar.dialogs

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.singandsongs.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapDialogFragment : DialogFragment(), OnMapReadyCallback {

  private lateinit var mMap: GoogleMap

  override fun onMapReady(googleMap: GoogleMap) {
    mMap = googleMap
    mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
    checkPermission()
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): android.app.Dialog {
    val builder = AlertDialog.Builder(requireContext())

    val view = layoutInflater.inflate(R.layout.map_dialog, null)

    val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
    mapFragment?.getMapAsync(this)

    builder.setView(view)
      .setTitle("Wybierz lokalizację: ")
      .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
      .setNegativeButton("Anuluj") { dialog, _ -> dialog.dismiss() }

    return builder.create()
  }

  private fun checkPermission() {
    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
      != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(requireActivity(),
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
      setLocal()
    }
  }

  private fun setLocal() {
    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
      == PackageManager.PERMISSION_GRANTED) {
      mMap.isMyLocationEnabled = true
    }
  }
}

