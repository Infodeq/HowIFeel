package com.slaw.howifeel.data.api.payload.symptomPost

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class SymptomRequest(

	@field:SerializedName("geo_location")
	val geoLocation: CoarseLocation,

//	@field:SerializedName("client_token")
//	val clientToken: String,

//	@field:SerializedName("report_time")
//	val reportTime: String,

	@field:SerializedName("symptoms")
	val symptoms: List<String>
)
data class CoarseLocation(
	@field:SerializedName("latitude")
	val latitude: String,
	@field:SerializedName("longitude")
	val longitude: String
)