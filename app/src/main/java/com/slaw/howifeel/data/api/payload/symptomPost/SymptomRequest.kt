package com.slaw.howifeel.data.api.payload.symptomPost

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SymptomRequest(

	@field:SerializedName("coarse_location")
	val coarseLocation: CoarseLocation,

	@field:SerializedName("client_token")
	val clientToken: String,

	@field:SerializedName("report_time")
	val reportTime: String,

	@field:SerializedName("health_symptoms")
	val healthSymptoms: List<String>
)
data class CoarseLocation(
	@field:SerializedName("latitude")
	val latitude: Double,
	@field:SerializedName("longitude")
	val longitude: Double
)