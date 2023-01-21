package com.uvpce.attendance_moodle_api_library

import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.math.BigInteger

class AttendanceRepository():iAttendanceRepository{
    override fun getMoodleUserID(context: Context, username: String, semester: Int,callback: ServerCallback) {

        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, MoodleAuth.URL[semester-1],
            { response ->
                try{
                    val outerArray = JSONArray(response)
                    val innerObjects = outerArray.getJSONObject(0)

                    val newjsonArray = JSONArray()
                    val newjsonObject = JSONObject()
                    newjsonObject.put("id",innerObjects.getString("id"))

                    newjsonArray.put(newjsonObject)
                    callback.onSuccess(newjsonArray)
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
                params["wstoken"] = MoodleAuth.CORE_TOKEN
                params["wsfunction"] = "core_user_get_users_by_field"
                params["moodlewsrestformat"] = "json"
                params["field"] = "username"
                params["values[]"] = username
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

    override fun getMoodleUserCoursesList(context: Context, username: String, semester:Int,callback: ServerCallback) {

        val mRequestQueue = Volley.newRequestQueue(context)
        getMoodleUserID(context,username,7,object :ServerCallback{
            override fun onSuccess(result: JSONArray) {
                var userid= result.getJSONObject(0).getString("id")
                val request = object : StringRequest(
                    Method.POST, MoodleAuth.URL[semester-1],
                    { response ->
                        try{
                            val newjsonArray = JSONArray()
                            val newjsonObject = JSONObject()
                            val jsonarray = JSONArray(response)
                            for (i in 0 until jsonarray.length()) {
                                val jsonobject = jsonarray.getJSONObject(i)
                                newjsonObject.put(jsonobject.getString("fullname"),jsonobject.getString("id").toString())
                            }
                            newjsonArray.put(newjsonObject)
                            callback.onSuccess(newjsonArray)
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
                        params["wstoken"] = MoodleAuth.CORE_TOKEN
                        params["wsfunction"] = "core_enrol_get_users_courses"
                        params["moodlewsrestformat"] = "json"
                        params["userid"] = userid
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

            override fun onError(result: String) {
                callback.onError(result)
            }
        })
    }

    override fun createAttendanceMoodle(
        context: Context,
        courseid: String,
        attendancename: String,
        semester: Int,
        callback: ServerCallback
    ) {

        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, MoodleAuth.URL[semester-1],
            { response ->
                try{
                    val outerArray = JSONArray()
                    outerArray.put(response)
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
                params["wstoken"] = MoodleAuth.ATTENDANCE_TOKEN
                params["wsfunction"] = "mod_attendance_add_attendance"
                params["moodlewsrestformat"] = "json"
                params["courseid"] = courseid
                params["name"] = attendancename
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

    override fun createSessionMoodle(
        context: Context,
        courseid: String,
        attendancename: String,
        sessiontime:String,
        duration:String,
        groupid:String,
        semester: Int,
        callback: ServerCallback
    ) {
//        createAttendanceMoodle(context,courseid,attendancename,7,object : ServerCallback{
//            override fun onSuccess(result: JSONArray)
//            {
//                var attendanceid = result.getJSONObject(0).getString("attendanceid")
//
//
//            }
//
//            override fun onError(result: String)
//            {
//                callback.onError(result)
//            }
//        })
        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, MoodleAuth.URL[semester-1],
            { response ->
                try{
                    val pattern1 = Regex("[0-9]{1,}")
                    val ans : MatchResult? = pattern1.find(response, 0)
                    val outerArray = JSONArray()
                    val innnerJsonObject = JSONObject()
                    innnerJsonObject.put("id",ans ?.value)
                    outerArray.put(innnerJsonObject)
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
                params["wstoken"] = MoodleAuth.ATTENDANCE_TOKEN
                params["wsfunction"] = "mod_attendance_add_session"
                params["moodlewsrestformat"] = "json"
                params["attendanceid"] = "37"
                params["sessiontime"] = sessiontime
//                        params["duration"] = duration
//                        params["groupid"] = groupid
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