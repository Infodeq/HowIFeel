package com.slaw.howifeel.data

import com.slaw.howifeel.data.api.ApiManager
import javax.inject.Inject

interface DataManager
class DataManagerImp @Inject constructor(
    val apiManager: ApiManager
): DataManager {
}