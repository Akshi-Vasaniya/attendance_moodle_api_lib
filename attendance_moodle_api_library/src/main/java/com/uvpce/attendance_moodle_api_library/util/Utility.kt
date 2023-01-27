package com.uvpce.attendance_moodle_api_library.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.StrictMode
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class Utility {
    fun getCurrenMillis():Long{
        val cal = Calendar.getInstance()
        return cal.timeInMillis
    }
    fun getCurrenDateTime():String{
        val cal = Calendar.getInstance()
        val df = SimpleDateFormat("dd-MM-yyyy hh:mm a")
        return df.format(cal.time)
    }
    fun getMillis(hour:Int, min:Int):Long{
        val setCalc = Calendar.getInstance()
        setCalc[Calendar.HOUR_OF_DAY] = hour
        setCalc[Calendar.MINUTE] = min
        setCalc[Calendar.SECOND] = 0
        return setCalc.timeInMillis
    }
    fun getDurationInSeconds(hour:Int, min:Int):Long{
        return hour * 60 * 60 + min * 60.toLong()
    }
    fun getSeconds(hour:Int, min:Int):Long{
        return getMillis(hour,min)/1000
    }
    fun getMillis(date:Int, month:Int, year:Int,hour:Int, min:Int):Long{
        val setCalc = Calendar.getInstance()
        setCalc[Calendar.DATE] = date
        setCalc[Calendar.MONTH] = month
        setCalc[Calendar.YEAR] = year
        setCalc[Calendar.HOUR_OF_DAY] = hour
        setCalc[Calendar.MINUTE] = min
        setCalc[Calendar.SECOND] = 0
        return setCalc.timeInMillis
    }
    fun getSeconds(date:Int, month:Int, year:Int,hour:Int, min:Int):Long{
        return getMillis(date,month,year,hour,min)/1000
    }
    fun convertUrlToBase64(url: String): String {
        val newurl: URL
        val bitmap: Bitmap
        var base64: String = ""
        try {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            newurl = URL(url)
            bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream())
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            base64 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return base64
    }
    fun convertBase64StringToImage(base64encodedData: String): Bitmap {
        val decodedByte = Base64.decode(base64encodedData, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
    }
}