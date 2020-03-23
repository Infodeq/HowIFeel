package com.slaw.howifeel.data.api.payload.loginRequest

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class LoginRequest(

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("year_born")
	val yearBorn: String? = null
)