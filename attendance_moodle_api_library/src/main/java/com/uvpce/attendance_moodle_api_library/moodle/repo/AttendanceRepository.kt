package com.example.guniattendancefaculty.moodle.repo

import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.uvpce.attendance_moodle_api_library.ServerCallback
import com.uvpce.attendance_moodle_api_library.moodle.Auth
import org.json.JSONArray

class AttendanceRepository(val context: Context) {

    val URL = Auth.url
    val core_token= Auth.core_token
    val attendance_token=Auth.attendance_token
    val file_token=Auth.file_token
    fun getMoodleServerUrl():String {return "$URL/webservice/rest/server.php" }


//    fun getTeacherUserByCourseGroup(context: Context, courseid: String,groupid:String ,callback: ServerCallback) {
//
//        val mRequestQueue = Volley.newRequestQueue(context)
//        val request = object : StringRequest(
//            Method.POST, getMoodleServerUrl(),
//            { response ->
//                try{
//                    val outerArray = JSONArray(response)
//
//                    callback.onSuccess(outerArray)
//                }
//                catch (ex:Exception)
//                {
//                    callback.onError(response)
//                }
//            },
//            { error ->
//                callback.onError(error.toString())
//            })
//        {
//            override fun getParams(): Map<String, String> {
//                val params: MutableMap<String, String> = HashMap()
//                params["wstoken"] = core_token
//                params["wsfunction"] = "core_enrol_get_enrolled_users"
//                params["moodlewsrestformat"] = "json"
//                params["courseid"] = courseid
//                params["options[0][name]"] = "roleid"
//                params["options[0][value]"] = "3"
//                return params
//            }
//
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): Map<String, String> {
//                val params: MutableMap<String, String> = HashMap()
//                params["Content-Type"] = "application/x-www-form-urlencoded"
//                return params
//            }
//        }
//        mRequestQueue.add(request)
//
//    }


    fun getEnrolledUserByCourseGroup(context: Context, courseid: String,groupid:String ,callback: ServerCallback) {

        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    val outerArray = JSONArray(response)

                    callback.onSuccess(outerArray)
                }
                catch (ex:Exception)
                {
                    callback.onError(response)
                }
            },
            { error ->
                callback.onError(error.toString())
            })
        {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["wstoken"] = core_token
                params["wsfunction"] = "core_enrol_get_enrolled_users"
                params["moodlewsrestformat"] = "json"
                params["courseid"] = courseid
                params["options[0][name]"] = "groupid"
                params["options[0][value]"] = groupid
                params["options[1][name]"] = "groupid"
                params["options[1][value]"] = "'username, profileimageurl, fullname'"
                return params
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] = "application/x-www-form-urlencoded"
                return params
            }
        }
        mRequestQueue.add(request)

    }


}