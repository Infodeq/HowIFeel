package com.slaw.howifeel.data.api

import com.slaw.howifeel.data.api.payload.loginRequest.LoginRequest
import com.slaw.howifeel.data.api.payload.otpResponse.OtpResponse
import com.slaw.howifeel.data.api.payload.otpResponse.OtpSendRequest
import com.slaw.howifeel.data.api.payload.symptomPost.SymptomRequest
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

interface ApiManager {
    fun login(loginRequestType: LoginRequest): Completable
    fun sendOtp(otpSendRequest: OtpSendRequest): Single<OtpResponse>
    fun sendSymptom(symptomRequest: SymptomRequest): Completable
}
@Singleton
class ApiManagerImpl @Inject constructor(
    private val webService: WebService
): ApiManager {
    override fun login(loginRequestType: LoginRequest) = webService.auth(loginRequestType)
    override fun sendOtp(otpSendRequest: OtpSendRequest) = webService.sendOtp(otpSendRequest)
    override fun sendSymptom(symptomRequest: SymptomRequest) = webService.sendSymptom(symptomRequest)

}