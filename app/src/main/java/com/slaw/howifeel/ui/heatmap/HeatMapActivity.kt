package com.slaw.howifeel.ui.heatmap

import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.slaw.howifeel.R
import com.slaw.howifeel.component.ActivityScope
import com.slaw.howifeel.component.ApplicationComponent
import com.slaw.howifeel.ui.base.BaseActivity
import com.slaw.howifeel.ui.login.appComponent
import com.slaw.howifeel.ui.main.getGPS
import dagger.BindsInstance
import dagger.Component
import org.jetbrains.anko.toast
import timber.log.Timber
import javax.inject.Inject


@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [HeatMapModule::class]
)
interface HeatMapActivityComponent {
    fun inject(mainActivity: HeatMapActivity)
    fun presenter(): HeatmapContract.Presenter
    @Component.Builder
    interface Builder {
        fun build(): HeatMapActivityComponent
        fun appComponent(component: ApplicationComponent): Builder

        @BindsInstance
        fun view(view: HeatmapContract.View?): Builder
    }
}
class HeatMapActivity : BaseActivity(),HeatmapContract.View, OnMapReadyCallback {

    @Inject
    lateinit var presenter: HeatmapContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerHeatMapActivityComponent.builder()
            .appComponent(appComponent())
            .view(this)
            .build()
            .inject(this)
        setContentView(R.layout.activity_heat_map)
        setupMap()
    }

    private fun setupMap() {
        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!.getMapAsync(
            this
        )
    }

    var locations = mutableListOf<LatLng>()
    lateinit var map: GoogleMap
    override fun showHeatmap(geoLocationList: MutableList<LatLng>) {
        locations = geoLocationList
        if(locations.isEmpty()){
            toast("Sorry. No data yet.")
            return
        }
        val mProvider = HeatmapTileProvider.Builder().data(locations)
            .build()
        val mOverlay = map
            .addTileOverlay(TileOverlayOptions().tileProvider(mProvider))

    }

    override fun onMapReady(map: GoogleMap) {
        Timber.d("map ready")
        presenter.start()
        this.map = map
        updateLocationUI()
    }

    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            map.isMyLocationEnabled = true
            map.uiSettings.isMyLocationButtonEnabled = true
            // Creating a LatLng object for the current location
            // Creating a LatLng object for the current location
            val gps = getGPS(this)
            val latLng = LatLng(gps[0], gps[1])
            // Showing the current location in Google Map
            // Showing the current location in Google Map
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            // Zoom in the Google Map
            // Zoom in the Google Map
            map.animateCamera(CameraUpdateFactory.zoomTo(3.5f))
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message)
        }
    }

}
