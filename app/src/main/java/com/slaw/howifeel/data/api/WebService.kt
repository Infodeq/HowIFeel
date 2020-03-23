package com.slaw.howifeel.data.api

import com.slaw.howifeel.data.api.payload.loginRequest.LoginRequest
import com.slaw.howifeel.data.api.payload.otpResponse.OtpResponse
import com.slaw.howifeel.data.api.payload.otpResponse.OtpSendRequest
import com.slaw.howifeel.data.api.payload.symptomPost.SymptomRequest
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface WebService {
    @POST("auth/register/")
    fun auth(@Body loginRequest: LoginRequest): Completable

    @POST("auth/register-ack/")
    fun sendOtp(@Body otpSendRequest: OtpSendRequest): Single<OtpResponse>

    @POST("health/status/")
    fun sendSymptom(@Body symptomRequest: SymptomRequest): Completable
}