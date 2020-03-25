package com.slaw.howifeel.data

import com.slaw.howifeel.data.api.ApiManager
import com.slaw.howifeel.data.api.payload.apiResponse.ApiResponse
import com.slaw.howifeel.data.api.payload.loginRequest.LoginRequest
import com.slaw.howifeel.data.api.payload.otpResponse.OtpResponse
import com.slaw.howifeel.data.api.payload.otpResponse.OtpSendRequest
import com.slaw.howifeel.data.api.payload.symptomPost.SymptomRequest
import com.slaw.howifeel.data.prefs.SharedPreferenceManager
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

interface DataManager {
    fun login(countryCode: String, phoneNumber: String, gender: String, yearBirth: String): Completable
    fun otpEntered(code: String): Single<ApiResponse>
    fun sendSymptom(symptomRequest: SymptomRequest): Completable
    fun downloadCoordinates(): Single<Response<ResponseBody>>
    var shownSymptom: Boolean
    val clientToken: String
}
@Singleton
class DataManagerImp @Inject constructor(
    private val apiManager: ApiManager,
    private val sharedPreferenceManager: SharedPreferenceManager
): DataManager {
    override fun login(
        countryCode: String,
        phoneNumber: String,
        gender: String,
        yearBirth: String
    ): Completable {
        val loginRequestType =  LoginRequest(
            phoneNumber,countryCode, gender, yearBirth
        )
        return apiManager.login(loginRequestType)
    }

    override fun otpEntered(code: String): Single<ApiResponse>{
        val otpSendRequest = OtpSendRequest(code)
        return apiManager.sendOtp(otpSendRequest)
            .doOnSuccess {
                sharedPreferenceManager.clientToken = it.response.authToken
            }
    }

    override fun sendSymptom(symptomRequest: SymptomRequest):Completable{
        return apiManager.sendSymptom(sharedPreferenceManager.clientToken, symptomRequest)
    }

    override fun downloadCoordinates() = apiManager.downloadCoordinates()

    override var shownSymptom = sharedPreferenceManager.shownSymptom

    override val clientToken: String
        get() = sharedPreferenceManager.clientToken
}