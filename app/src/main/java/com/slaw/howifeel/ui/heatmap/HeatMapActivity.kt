package com.slaw.howifeel.ui.heatmap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.slaw.howifeel.R
import com.slaw.howifeel.component.ActivityScope
import com.slaw.howifeel.component.ApplicationComponent
import com.slaw.howifeel.ui.login.appComponent
import dagger.BindsInstance
import dagger.Component
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
class HeatMapActivity : AppCompatActivity(),HeatmapContract.View, OnMapReadyCallback {

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
        val mProvider = HeatmapTileProvider.Builder().data(locations)
            .build()
        val mOverlay = map
            .addTileOverlay(TileOverlayOptions().tileProvider(mProvider))

    }

    override fun onMapReady(map: GoogleMap) {
        Timber.d("map ready")
        presenter.start()
        this.map = map
    }
}
