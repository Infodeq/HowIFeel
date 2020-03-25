package com.slaw.howifeel.ui.heatmap

import com.google.android.gms.maps.model.LatLng

interface HeatmapContract {
    interface View {
        fun showHeatmap(geoLocationList: MutableList<LatLng>)
    }

    interface Presenter {
        fun detach()
        fun start()
    }
}