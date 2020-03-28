package com.slaw.howifeel.data.api

import com.slaw.howifeel.data.api.payload.apiResponse.ApiResponse
import com.slaw.howifeel.data.api.payload.loginRequest.LoginRequest
import com.slaw.howifeel.data.api.payload.otpResponse.OtpResponse
import com.slaw.howifeel.data.api.payload.otpResponse.OtpSendRequest
import com.slaw.howifeel.data.api.payload.symptomPost.SymptomRequest
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

interface ApiManager {
    fun login(loginRequestType: LoginRequest): Single<ApiResponse>
    fun sendOtp(otpSendRequest: OtpSendRequest): Single<ApiResponse>
    fun sendSymptom(accessToken: String, symptomRequest: SymptomRequest): Completable
    fun downloadCoordinates(): Single<Response<ResponseBody>>
}
@Singleton
class ApiManagerImpl @Inject constructor(
    private val webService: WebService
): ApiManager {
    override fun login(loginRequestType: LoginRequest) = webService.auth(loginRequestType)
    override fun sendOtp(otpSendRequest: OtpSendRequest) = webService.sendOtp(otpSendRequest)
    override fun sendSymptom(accessToken: String,symptomRequest: SymptomRequest) = webService.sendSymptom("access_token=$accessToken",symptomRequest)
    override fun downloadCoordinates() = webService.downloadCoordinates()

}