package com.example.guniattendancefaculty.moodle

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.StrictMode
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.net.URL

class Utility {
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