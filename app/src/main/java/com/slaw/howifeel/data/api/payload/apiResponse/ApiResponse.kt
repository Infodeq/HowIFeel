package com.slaw.howifeel.data.api.payload.apiResponse

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
import com.slaw.howifeel.data.api.payload.otpResponse.OtpResponse

@Generated("com.robohorse.robopojogenerator")
data class ApiResponse(

	@field:SerializedName("schema")
	val schema: String? = null,

	@field:SerializedName("resource")
	val resource: String,

	@field:SerializedName("response")
	val response: OtpResponse,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
/*
*
{"schema":"infodeq:reply.1","resource":"auth/register-phone-ack/","text":"Success","status":200,"response":{"auth_token":"ey"}}
* */