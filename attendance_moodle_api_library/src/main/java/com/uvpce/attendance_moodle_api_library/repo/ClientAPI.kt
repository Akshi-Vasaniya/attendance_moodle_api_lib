package com.uvpce.attendance_moodle_api_library.repo

import android.app.AlertDialog
import android.content.Context
import org.json.JSONObject

class ClientAPI {
    companion object{
        const val attendanceToken = "697859a828111d63c3f68543ac986827"
        const val coreToken = "4f3c9f8f0404a7db50825391c295937e"
        const val fileUploadToken = "8d29dd97dd7c93b0e3cdd43d4b797c87"
        private const val userDefaultPicURL =
            "/webservice/pluginfile.php/89/user/icon/f1?token=4f3c9f8f0404a7db50825391c295937e"
        fun getDefaultUserPictureUrl(moodleUrl:String):String{
            return "$moodleUrl$userDefaultPicURL"
        }
        /*val courseAttendaceMap = mapOf(
            "35" to mapOf("name" to "Attendance", "id" to "52"),
        )*/
        val courseAttendaceMap = JSONObject()
        init {
            courseAttendaceMap.put("35",getJsonObject("58","Attendance"))
            courseAttendaceMap.put("34",getJsonObject("59","Attendance"))
            courseAttendaceMap.put("26",getJsonObject("60","Attendance"))
            courseAttendaceMap.put("25",getJsonObject("61","Attendance"))
            courseAttendaceMap.put("37",getJsonObject("62","Attendance"))
            courseAttendaceMap.put("36",getJsonObject("63","Attendance"))
            courseAttendaceMap.put("27",getJsonObject("64","Attendance"))
            courseAttendaceMap.put("28",getJsonObject("65","Attendance"))
            courseAttendaceMap.put("39",getJsonObject("66","Attendance"))
            courseAttendaceMap.put("38",getJsonObject("67","Attendance"))
            courseAttendaceMap.put("30",getJsonObject("68","Attendance"))
            courseAttendaceMap.put("29",getJsonObject("69","Attendance"))
            courseAttendaceMap.put("41",getJsonObject("70","Attendance"))
            courseAttendaceMap.put("40",getJsonObject("71","Attendance"))
            courseAttendaceMap.put("31",getJsonObject("72","Attendance"))
            courseAttendaceMap.put("32",getJsonObject("73","Attendance"))

        }
        fun getJsonObject(id:String,name:String):JSONObject{
            val jsonObject = JSONObject()
            jsonObject.put("id",id)
            jsonObject.put("name",name)
            return jsonObject
        }
        fun showErrorBox(context: Context, title: String, msg: String, negB:String = "", posB:String = "", cancelable: Boolean = true){
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle(title)
            alertDialogBuilder
                .setMessage(msg)
                .setCancelable(cancelable)
                .setNegativeButton(negB) { dialog, id ->
                    dialog.cancel()
                }
                .setPositiveButton(posB) { dialog, id ->
                    dialog.cancel()
                }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

}