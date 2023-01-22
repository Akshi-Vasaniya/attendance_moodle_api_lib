package com.uvpce.attendance_moodle_api_library

import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

/**
 * You can access all Moodle-related functions by using the AttendanceRepository class.
 * @param MoodleURL String
 * @param CORE_TOKEN String
 * @param ATTENDANCE_TOKEN String
 *
 * @return Object
 */
class AttendanceRepository(val URL:String, val CORE_TOKEN: String, val ATTENDANCE_TOKEN:String):iAttendanceRepository{

    override fun getMoodleUserID(context: Context, username: String,callback: ServerCallback) {

        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, URL,
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
                params["wstoken"] = CORE_TOKEN
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

    override fun getMoodleUserCoursesList(context: Context, username: String,callback: ServerCallback) {
        val mRequestQueue = Volley.newRequestQueue(context)
        getMoodleUserID(context,username,object :ServerCallback{
            override fun onSuccess(result: JSONArray) {
                var userid= result.getJSONObject(0).getString("id")
                val request = object : StringRequest(
                    Method.POST, URL,
                    { response ->
                        try{
                            val newjsonArray = JSONArray()

                            val jsonarray = JSONArray(response)
                            for (i in 0 until jsonarray.length()) {
                                val jsonobject = jsonarray.getJSONObject(i)
                                val newjsonObject = JSONObject()
                                newjsonObject.put("courseid",jsonobject.getString("id"))
                                newjsonObject.put("coursename",jsonobject.getString("fullname"))
                                newjsonArray.put(newjsonObject)
                            }
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
                        params["wstoken"] = CORE_TOKEN
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

    override fun createAttendanceMoodle(context: Context, course_id: String, attandance_name:String, callback: ServerCallback)
    {
        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, URL,
            { response ->
                try{
                    val attendance_id_pattern = Regex("[0-9]{1,}")
                    val attendance_id = attendance_id_pattern.find(response, 0)
                    if (attendance_id != null) {
                        val arrayJSON = JSONArray()
                        val objectJSON = JSONObject()
                        objectJSON.put("id",attendance_id.value)
                        arrayJSON.put(objectJSON)
                        callback.onSuccess(arrayJSON)
                    } else{
                        callback.onError(response)
                    }
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
                params["wstoken"] = ATTENDANCE_TOKEN
                params["wsfunction"] = "mod_attendance_add_attendance"
                params["moodlewsrestformat"] = "json"
                params["courseid"] = course_id;
                params["name"] = attandance_name;
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
        course_id:String,
        attandance_name:String,
        session_time:String,
        duration:String,
        group_id:String,
        callback: ServerCallback
    ) {
        createAttendanceMoodle(context,course_id,attandance_name,object :ServerCallback{
            override fun onSuccess(result: JSONArray) {
                var attandance_id = result.getJSONObject(0).getString("id")
                val mRequestQueue = Volley.newRequestQueue(context)
                val request = object : StringRequest(
                    Method.POST, URL,
                    { response ->
                        try{
                            val session_id_pattern = Regex("[0-9]{1,}")
                            val session_id = session_id_pattern.find(response, 0)
                            if (session_id != null) {
                                val arrayJSON = JSONArray()
                                val objectJSON = JSONObject()
                                objectJSON.put("id",session_id.value)
                                arrayJSON.put(objectJSON)
                                callback.onSuccess(arrayJSON)
                            }
                            else{
                                callback.onError(response)
                            }

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
                        params["wstoken"] = ATTENDANCE_TOKEN
                        params["wsfunction"] = "mod_attendance_add_session"
                        params["moodlewsrestformat"] = "json"
                        params["attendanceid"]= attandance_id
                        params["sessiontime"] = session_time
                        params["duration"] = duration
//                        params["groupid"] = group_id
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

    override fun getCourseGroups(context: Context, course_id: String, callback: ServerCallback) {
        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, URL,
            { response ->
                try{
                    val arrayJSON = JSONArray(response)
                    val newArryJSON = JSONArray()

                    for (i in 0 until arrayJSON.length()) {
                        val jsonobject = arrayJSON.getJSONObject(i)
                        val objectJSON = JSONObject()
                        objectJSON.put("groupid",jsonobject.getString("id"))
                        objectJSON.put("groupname",jsonobject.getString("name"))
                        newArryJSON.put(objectJSON)
                    }
                    callback.onSuccess(newArryJSON)
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
                params["wstoken"] = CORE_TOKEN
                params["wsfunction"] = "core_group_get_course_groups"
                params["moodlewsrestformat"] = "json"
                params["courseid"] = course_id
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