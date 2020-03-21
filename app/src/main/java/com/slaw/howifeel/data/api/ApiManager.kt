package com.slaw.howifeel.data.api

import javax.inject.Inject

interface ApiManager {
}
class ApiManagerImpl @Inject constructor(
    private val webService: WebService
): ApiManager {

}