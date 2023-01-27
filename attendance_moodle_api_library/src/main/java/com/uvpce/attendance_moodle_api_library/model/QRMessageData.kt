package com.uvpce.attendance_moodle_api_library.model

import android.util.Base64
import android.util.Log
import org.json.JSONObject

class QRMessageData(
    val session: MoodleSession,
    val loggedInFacultyUserId:String,
    val attendanceByFacultyId:String,
    val facultyLocationLat:String,
    val facultyLocationLong:String,
    val attendanceStartDate:Long,
    val attendanceEndDate:Long,
    val attendanceDuration:Long): ModelBase {

    override fun toJsonObject(): JSONObject {
        val json = JSONObject()
        json.put("session",session.toJsonObject())
        json.put("loggedInFacultyUserId",loggedInFacultyUserId)
        json.put("attendanceByFacultyId",attendanceByFacultyId)
        json.put("facultyLocationLat",facultyLocationLat)
        json.put("facultyLocationLong",facultyLocationLong)
        json.put("attendanceStartDate",attendanceStartDate)
        json.put("attendanceEndDate",attendanceEndDate)
        json.put("attendanceDuration",attendanceDuration)
        return json
    }

    override fun toString(): String {
        val jsonObject = toJsonObject()
        return jsonObject.toString(4)
    }
    companion object{
        fun fromJsonObject(jsonString: String):QRMessageData {
            val jsonObject = JSONObject(jsonString)
            val session = MoodleSession.fromJsonObject(jsonObject.getString("session"))
            return QRMessageData(
                session,
                jsonObject.getString("loggedInFacultyUserId"),
                jsonObject.getString("attendanceByFacultyId"),
                jsonObject.getString("facultyLocationLat"),
                jsonObject.getString("facultyLocationLong"),
                jsonObject.getLong("attendanceStartDate"),
                jsonObject.getLong("attendanceEndDate"),
                jsonObject.getLong("attendanceDuration"),
            )
        }
        fun getQRMessageObject(QrCodeMessage:String,
                               onSuccess:(QRMessageData)->Unit,
                               onError:(String)->Unit)
        {
            try {
                val jsonString = String(Base64.decode(QrCodeMessage,Base64.DEFAULT))
                Log.i(this::class.java.name, "getQRMessageObject: String Input:$jsonString")
                onSuccess(QRMessageData.fromJsonObject(jsonString))
            }
            catch (e:Exception){
                onError("Errors: ${e}")
            }
        }
        fun getQRMessageString(QrCodeMessageObject:QRMessageData,
                               onSuccess:(String)->Unit,
                               onError:(String)->Unit){
            try {
                onSuccess(Base64.encodeToString(QrCodeMessageObject.toString().toByteArray(),Base64.DEFAULT))
            }
            catch (e:Exception){
                onError("Errors: ${e}")
            }

        }
    }
}