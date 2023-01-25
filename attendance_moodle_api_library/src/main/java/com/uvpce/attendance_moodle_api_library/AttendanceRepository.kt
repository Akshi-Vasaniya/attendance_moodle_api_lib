package com.uvpce.attendance_moodle_api_library

import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * You can access all Moodle-related functions by using the AttendanceRepository class.
 * @param MoodleURL String
 * @param CORE_TOKEN String
 * @param ATTENDANCE_TOKEN String
 * @param UPLOAD_FILE_TOKEN String
 * @return Object
 */
class AttendanceRepository(val URL:String, val CORE_TOKEN: String, val ATTENDANCE_TOKEN:String, val UPLOAD_FILE_TOKEN:String):iAttendanceRepository{

    fun getMoodleServerUrl():String {return "$URL/webservice/rest/server.php" }

    override fun getUserInfoMoodle(context: Context, username: String,callback: ServerCallback) {

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

    override fun getFacultyInfoMoodle(
        context: Context,
        faculty_name: String,
        callback: ServerCallback
    ) {
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
                params["wstoken"] = CORE_TOKEN
                params["wsfunction"] = "core_user_get_users_by_field"
                params["moodlewsrestformat"] = "json"
                params["field"] = "username"
                params["values[]"] = faculty_name
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

    override fun getUserCoursesListMoodle(context: Context, username: String,callback: ServerCallback) {
        val mRequestQueue = Volley.newRequestQueue(context)
        getUserInfoMoodle(context,username,object :ServerCallback{
            override fun onSuccess(result: JSONArray) {
                var userid= result.getJSONObject(0).getString("id")
                val request = object : StringRequest(
                    Method.POST, getMoodleServerUrl(),
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

    override fun createAttendanceMoodle(context: Context, course_id: String, attendance_name:String, callback: ServerCallback)
    {
        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
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
                params["name"] = attendance_name;
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
        attendance_id:String,
        session_time:String,
        duration:String,
        group_id:String,
        callback: ServerCallback
    ) {
        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
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
                params["attendanceid"]= attendance_id
                params["sessiontime"] = session_time
                params["duration"] = duration
                params["groupid"] = group_id
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

    override fun getCourseGroupsMoodle(context: Context, course_id: String, callback: ServerCallback) {
        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
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

    override fun sendMessageMoodle(context: Context, user_id:String, attendance_id:String,course_id:String,faculty_id:String, faculty_location:String, group_id:String, session_id:String, start_time_of_attendance:String, end_time_of_attendance:String, callback: ServerCallback) {
        val textJsonObject = JSONObject()
        textJsonObject.put("faculty_id",faculty_id)
        textJsonObject.put("faculty_loc",faculty_location)
        textJsonObject.put("session_id",session_id)
        textJsonObject.put("attendance_id",attendance_id)
        textJsonObject.put("course_id",course_id)
        textJsonObject.put("group_id",group_id)
        textJsonObject.put("start_time_of_attendance",start_time_of_attendance)
        textJsonObject.put("end_time_of_attendance",end_time_of_attendance)
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
                params["wstoken"] = CORE_TOKEN
                params["wsfunction"] = "core_message_send_instant_messages"
                params["moodlewsrestformat"] = "json"
                params["messages[0][touserid]"]=user_id
                params["messages[0][text]"]=textJsonObject.toString()
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

    override fun takeAttendanceMoodle(
        context: Context,
        session_id: String,
        student_id: String,
        taken_by_id: String,
        status_id: String,
        status_set: String,
        callback: ServerCallback
    ) {
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
                params["wstoken"] = ATTENDANCE_TOKEN
                params["wsfunction"] = "mod_attendance_update_user_status"
                params["moodlewsrestformat"] = "json"
                params["sessionid"] = session_id
                params["studentid"] = student_id
                params["takenbyid"] = taken_by_id
                params["statusid"] = status_id
                params["statusset"] = status_set
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

    override fun getSessionsListMoodle(
        context: Context,
        attendance_id: String,
        callback: ServerCallback
    ) {
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
                params["wstoken"] = ATTENDANCE_TOKEN
                params["wsfunction"] = "mod_attendance_get_sessions"
                params["moodlewsrestformat"] = "json"
                params["attendanceid"] = attendance_id
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

    override fun uploadFileMoodle(
        context: Context,
        component: String,
        file_area: String,
        item_id: String,
        file_path: String,
        file_name: String,
        file_content: String,
        context_level: String,
        instanceid: String,
        callback: ServerCallback
    ) {
        val queue = Volley.newRequestQueue(context)
        val request: StringRequest = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    var jsonObject = JSONObject(response)
                    var jsonArray = JSONArray()
                    jsonArray.put(jsonObject)
                    callback.onSuccess(jsonArray)
                }
                catch (e:Exception)
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
                params["wstoken"] = UPLOAD_FILE_TOKEN
                params["wsfunction"] = "core_files_upload"
                params["moodlewsrestformat"] = "json"
                params["component"] = component
                params["filearea"] = file_area
                params["itemid"] = item_id
                params["filepath"] = file_path
                params["filename"] = file_name
                params["filecontent"] = file_content
                params["contextlevel"] = context_level
                params["instanceid"] = instanceid
                return params
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] = "application/x-www-form-urlencoded"
                return params
            }
        }
        queue.add(request)
    }

    override fun updatePictureMoodle(
        context: Context,
        draft_item_id: String,
        user_id: String,
        callback: ServerCallback
    ) {
        val queue = Volley.newRequestQueue(context)
        val request: StringRequest = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    var jsonObject = JSONObject(response)
                    var jsonArray = JSONArray()
                    jsonArray.put(jsonObject)
                    callback.onSuccess(jsonArray)
                }
                catch (e:Exception)
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
                params["wsfunction"] = "core_user_update_picture"
                params["moodlewsrestformat"] = "json"
                params["draftitemid"] = draft_item_id
                params["userid"] = user_id
                return params
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] = "application/x-www-form-urlencoded"
                return params
            }
        }
        queue.add(request)
    }

    override fun getMessageMoodle(
        context: Context,
        user_id: String,
        type: String,
        read: String,
        callback: ServerCall
    ) {
        val queue = Volley.newRequestQueue(context)
        val request: StringRequest = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    var res = JSONArray().put(JSONObject(response))
                    res = JSONArray(res.getJSONObject(0).getString("messages"))
                    var msg = res.getJSONObject(0).getString("fullmessage")

                    callback.onSuccess(JSONObject(msg))
                }
                catch (e:Exception)
                {
                    callback.onError(e.message.toString())
                }

            },
            { error ->
                callback.onError(error.toString())
            })
        {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["wstoken"] = CORE_TOKEN
                params["wsfunction"] = "core_message_get_messages"
                params["moodlewsrestformat"] = "json"
                params["useridto"] = user_id
                params["type"] = type
                params["read"] = read
                return params
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] = "application/x-www-form-urlencoded"
                return params
            }
        }
        queue.add(request)
    }
    override fun getCategoriesMoodle(context: Context, callback: ServerCallback)
    {
        val queue = Volley.newRequestQueue(context)
        val request: StringRequest = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    callback.onSuccess(JSONArray(response))
                }
                catch (e:Exception)
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
                params["wsfunction"] = "core_course_get_categories"
                params["moodlewsrestformat"] = "json"
                return params
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] = "application/x-www-form-urlencoded"
                return params
            }
        }
        queue.add(request)
    }

    override fun getCohorts(context: Context, callback: ServerCallback) {
        val queue = Volley.newRequestQueue(context)
        val request: StringRequest = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    callback.onSuccess(JSONArray(response))
                }
                catch (e:Exception)
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
                params["wstoken"] = UPLOAD_FILE_TOKEN
                params["wsfunction"] = "core_cohort_get_cohorts"
                params["moodlewsrestformat"] = "json"
                return params
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] = "application/x-www-form-urlencoded"
                return params
            }
        }
        queue.add(request)
    }
}