package com.slaw.howifeel.ui.heatmap

import android.os.Environment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.heatmaps.WeightedLatLng
import com.opencsv.CSVReader
import com.slaw.howifeel.data.DataManager
import com.slaw.howifeel.data.api.payload.symptomPost.CoarseLocation
import dagger.Binds
import dagger.Module
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.*
import java.lang.NumberFormatException
import javax.inject.Inject


@Module
abstract class HeatMapModule {
    @Binds
    abstract fun provideMainPresenter(presenter: HeatMapActivityPresenter): HeatmapContract.Presenter
}
class HeatMapActivityPresenter @Inject constructor(
    val dataManager: DataManager,
    var view: HeatmapContract.View?
): HeatmapContract.Presenter {
    private val compositeDisposable = CompositeDisposable()
    override fun detach() {
        compositeDisposable.dispose()
        view = null
    }

    override fun start() {
        compositeDisposable.add(
            dataManager.downloadCoordinates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        val response = it.body()?.string()!!
                        val coordinates = response.split("\n").filter {
                            !it.startsWith("#")
                        }.filter {
                            it.isNotHeader(it)
                        }.filter {
                            it.isNotEmpty()
                        }.map {
                                it.trim()
                            }
                        val geoLocationList = mutableListOf<LatLng>()
                        coordinates.forEach {
                                val split = it.split("\t")
                            val lat = split[0].toDouble()
                            val lon = split[1].toDouble()
                            if(lat!=0.toDouble()&&lon!=0.toDouble()){
                                geoLocationList.add(
                                    LatLng(
                                        split[0].toDouble(),split[1].toDouble()
                                    )
                                )
                            }
                        }
                        view?.showHeatmap(geoLocationList)
                    }, onError = {

                    }
                )
        )
    }

    private fun readCsv() {
        try {
            val path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS
            )
            val csvFile = File(path, "csvfile.csv")

            val reader = CSVReader(FileReader(csvFile.absolutePath))
            var nextLine: Array<String>
            while (reader.readNext().also {
                    nextLine = it
                } != null) { // nextLine[] is an array of values from the line
                Timber.d(nextLine[0] + nextLine[1] + "etc...")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.e(e.localizedMessage)
//            Toast.makeText(this, "The specified file was not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun writeResponseBodyToDisk(body: ResponseBody): Boolean {
        return try { // todo change the file location/name according to your needs
            val path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS
            )
            val futureStudioIconFile =
                File(path, "csvfile.csv")
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                path.mkdirs();
                val fileReader = ByteArray(4096)
                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0
                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)
                while (true) {
                    val read: Int = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
//                    Log.d(TAG, "file download: $fileSizeDownloaded of $fileSize")
                }
                outputStream.flush()
                true
            } catch (e: IOException) {
                false
            } finally {
                if (inputStream != null) {
                    inputStream.close()
                }
                if (outputStream != null) {
                    outputStream.close()
                }
            }
        } catch (e: IOException) {
            false
        }
    }
}

private fun String.isNotHeader(text: String): Boolean {
    return !text.contains("lat")
}
