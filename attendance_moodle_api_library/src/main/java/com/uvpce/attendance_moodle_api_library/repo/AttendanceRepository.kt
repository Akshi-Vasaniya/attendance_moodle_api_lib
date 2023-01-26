package com.uvpce.attendance_moodle_api_library.repo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.StrictMode
import android.util.Base64
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.uvpce.attendance_moodle_api_library.model.QRMessageData
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.net.URL

/**
 * You can access all Moodle-related functions by using the AttendanceRepository class.
 * @param MoodleURL String
 * @param CORE_TOKEN String
 * @param ATTENDANCE_TOKEN String
 * @param UPLOAD_FILE_TOKEN String
 * @return Object
 */
class AttendanceRepository(
    private val MoodleURL:String,
    val CORE_TOKEN: String,
    val ATTENDANCE_TOKEN:String,
    val UPLOAD_FILE_TOKEN:String): iAttendanceRepository {


    fun getMoodleServerUrl():String {return "$MoodleURL/webservice/rest/server.php" }

    override fun getUserInfoMoodle(context: Context, username: String,onSuccess:(JSONArray)->Unit,
                                   onError:(String)->Unit) {

        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    val outerArray = JSONArray(response)

                    onSuccess(outerArray)
                }
                catch (ex:Exception)
                {
                    onError(response)
                }
            },
            { error ->
                onError(error.toString())
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

    override fun getUserByFieldMoodle(
        context: Context,
        field: String,
        value: ArrayList<String>,
        onSuccess:(JSONArray)->Unit,
        onError:(String)->Unit
    ) {
        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    val outerArray = JSONArray(response)

                    onSuccess(outerArray)
                }
                catch (ex:Exception)
                {
                    onError(response)
                }
            },
            { error ->
                onError(error.toString())
            })
        {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["wstoken"] = CORE_TOKEN
                params["wsfunction"] = "core_user_get_users_by_field"
                params["moodlewsrestformat"] = "json"
                params["field"] = field
                for (i in 0 until value.size){
                    params["values[$i]"] = value[i]
                }
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
        onSuccess:(JSONArray)->Unit,
        onError:(String)->Unit
    ) {
        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    val outerArray = JSONArray(response)

                    onSuccess(outerArray)
                }
                catch (ex:Exception)
                {
                    onError(response)
                }
            },
            { error ->
                onError(error.toString())
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

    override fun getUserCoursesListMoodle(context: Context, username: String,onSuccess:(JSONArray)->Unit,
                                          onError:(String)->Unit) {
        val mRequestQueue = Volley.newRequestQueue(context)
        getUserInfoMoodle(context,username,onSuccess= {result->
                val userid= result.getJSONObject(0).getString("id")
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
                            onSuccess(newjsonArray)
                        }
                        catch (ex:Exception)
                        {
                            onError(response)
                        }

                    },
                    { error ->
                        onError(error.toString())
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
            },
            onError={result ->
                onError(result)
            }
        )
    }

    override fun createAttendanceMoodle(context: Context,
                                        course_id: String,
                                        attendance_name:String,
                                        onSuccess:(JSONArray)->Unit,
                                        onError:(String)->Unit)
    {
        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    val attendanceIdPattern = Regex("\\d+")
                    val attendanceId = attendanceIdPattern.find(response, 0)
                    if (attendanceId != null) {
                        val arrayJSON = JSONArray()
                        val objectJSON = JSONObject()
                        objectJSON.put("id",attendanceId.value)
                        QRMessageData.attendanceid_by_course[course_id]=attendanceId.value
                        QRMessageData.attendance_id=attendanceId.value.toInt()
                        arrayJSON.put(objectJSON)

                        onSuccess(arrayJSON)
                    } else{
                        onError(response)
                    }
                }
                catch (ex:Exception)
                {
                    onError(response)
                }
            },
            { error ->
                onError(error.toString())
            })
        {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["wstoken"] = ATTENDANCE_TOKEN
                params["wsfunction"] = "mod_attendance_add_attendance"
                params["moodlewsrestformat"] = "json"
                params["courseid"] = course_id
                params["name"] = attendance_name
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
        onSuccess:(JSONArray)->Unit,
        onError:(String)->Unit
    ) {
        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    val sessionIdPattern = Regex("\\d+")
                    val sessionId = sessionIdPattern.find(response, 0)
                    if (sessionId != null) {
                        val arrayJSON = JSONArray()
                        val objectJSON = JSONObject()
                        objectJSON.put("id",sessionId.value)
                        arrayJSON.put(objectJSON)
                        getSessionsListMoodle(context,attendance_id,
                            onError= { result ->
                                onError(result)
                            },onSuccess={result->
                                for(i in 0 until result.length())
                                {
                                    val jsonObj = result.getJSONObject(i)
                                    if(jsonObj.getString("id")==sessionId.value)
                                    {
                                        val sessend=(jsonObj.getString("sessdate").toLong() + jsonObj.getString("duration").toLong())

                                        val tempJSONObj = JSONObject()
                                        tempJSONObj.put("session_id",sessionId.value)
                                        tempJSONObj.put("session_start_time",session_time)
                                        tempJSONObj.put("session_end_time",sessend*1000)
                                        tempJSONObj.put("session_duration",duration.toLong()/60)
                                        QRMessageData.sessionData.put(tempJSONObj)
                                        break
                                    }
                                }
                                onSuccess(arrayJSON)

                            }
                        )

                    }
                    else{
                        onError(response)
                    }

                }
                catch (ex:Exception)
                {
                    onError(response)
                }
            },
            { error ->
                onError(error.toString())
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

    override fun getCourseGroupsMoodle(context: Context, course_id: String, onSuccess:(JSONArray)->Unit,
                                       onError:(String)->Unit) {
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
                    onSuccess(newArryJSON)
                }
                catch (ex:Exception)
                {
                    onError(response)
                }
            },
            { error ->
                onError(error.toString())
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

    override fun sendMessageMoodle(context: Context, user_id:String, attendance_id:String,
                                   course_id:String,faculty_id:String, faculty_location:String,
                                   group_id:String, session_id:String,
                                   start_time_of_attendance:String,
                                   end_time_of_attendance:String,
                                   onSuccess:(JSONArray)->Unit,
                                   onError:(String)->Unit) {
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
                    onSuccess(outerArray)
                }
                catch (ex:Exception)
                {
                    onError(response)
                }
            },
            { error ->
                onError(error.toString())
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
        onSuccess:(JSONArray)->Unit,
        onError:(String)->Unit
    ) {
        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    val outerArray = JSONArray(response)
                    onSuccess(outerArray)
                }
                catch (ex:Exception)
                {
                    onError(response)
                }
            },
            { error ->
                onError(error.toString())
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
        onSuccess:(JSONArray)->Unit,
        onError:(String)->Unit
    ) {
        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    val outerArray = JSONArray(response)

                    onSuccess(outerArray)
                }
                catch (ex:Exception)
                {
                    onError(response)
                }
            },
            { error ->
                onError(error.toString())
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
        onSuccess:(JSONArray)->Unit,
        onError:(String)->Unit
    ) {
        val queue = Volley.newRequestQueue(context)
        val request: StringRequest = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    val jsonObject = JSONObject(response)
                    val jsonArray = JSONArray()
                    jsonArray.put(jsonObject)
                    onSuccess(jsonArray)
                }
                catch (e:Exception)
                {
                    onError(response)
                }

            },
            { error ->
                onError(error.toString())
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
        onSuccess:(JSONArray)->Unit,
        onError:(String)->Unit
    ) {
        val queue = Volley.newRequestQueue(context)
        val request: StringRequest = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    val jsonObject = JSONObject(response)
                    val jsonArray = JSONArray()
                    jsonArray.put(jsonObject)
                    onSuccess(jsonArray)
                }
                catch (e:Exception)
                {
                    onError(response)
                }

            },
            { error ->
                onError(error.toString())
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
        onSuccess:(JSONObject)->Unit,
        onError:(String)->Unit
    ) {
        val queue = Volley.newRequestQueue(context)
        val request: StringRequest = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    var res = JSONArray().put(JSONObject(response))
                    res = JSONArray(res.getJSONObject(0).getString("messages"))
                    val msg = res.getJSONObject(0).getString("fullmessage")

                    onSuccess(JSONObject(msg))
                }
                catch (e:Exception)
                {
                    onError(e.message.toString())
                }

            },
            { error ->
                onError(error.toString())
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
    override fun getCategoriesMoodle(context: Context, onSuccess:(JSONArray)->Unit,
                                     onError:(String)->Unit)
    {
        val queue = Volley.newRequestQueue(context)
        val request: StringRequest = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    onSuccess(JSONArray(response))
                }
                catch (e:Exception)
                {
                    onError(response)
                }

            },
            { error ->
                onError(error.toString())
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

    override fun getCohortsMoodle(context: Context, onSuccess:(JSONArray)->Unit,
                                  onError:(String)->Unit) {
        val queue = Volley.newRequestQueue(context)
        val request: StringRequest = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    onSuccess(JSONArray(response))
                }
                catch (e:Exception)
                {
                    onError(response)
                }

            },
            { error ->
                onError(error.toString())
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

    override fun getCohortMembersMoodle(context: Context, cohort_id: Int, onSuccess:(JSONArray)->Unit,
                                        onError:(String)->Unit) {
        val queue = Volley.newRequestQueue(context)
        val request: StringRequest = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    val jsonArrayRes=JSONArray(response)
                    val jsonObjectRes = jsonArrayRes.getJSONObject(0)
                    val userIds = jsonObjectRes.getString("userids")

                    val pattern = Regex("\\d+")
                    val res1 : Sequence<MatchResult> = pattern.findAll(userIds, 0)
                    val userList = ArrayList<String>()
                    // Prints all the matches using forEach loop
                    res1.forEach()
                    {
                            matchResult -> userList.add(matchResult.value)
                    }

                   getUserByFieldMoodle(context,"id",userList,
                       onSuccess={result->
                           onSuccess(result)
                       }, onError={result->
                           onError(result)
                       }
                   )
                }
                catch (e:Exception)
                {
                    onError(response)
                }

            },
            { error ->
                onError(error.toString())
            })
        {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["wstoken"] = UPLOAD_FILE_TOKEN
                params["wsfunction"] = "core_cohort_get_cohort_members"
                params["moodlewsrestformat"] = "json"
                params["cohortids[]"] = cohort_id.toString()
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

    override fun resolveImgURLMoodle(
        url: String,
        token: String
    ): String {
        try{
            lateinit var finalurl: String
            val uri = Uri.parse(url)
            val bitmap: Bitmap
            val base64: String
            finalurl = "http://" + uri.host + "/webservice/"
            finalurl += url.substring(url.indexOf("pluginfile.php")) //For not Default Pic
            finalurl = finalurl.split("?")[0]
            finalurl += "?token=${token}"

            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val newUrl = URL(finalurl)
            bitmap = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream())
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            base64 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)

            return base64
        }
        catch (e:Exception)
        {
            return e.message.toString()
        }
    }

    override fun getEnrolledUserByCourseGroupMoodle(
        context: Context,
        courseid: String,
        groupid: String,
        onSuccess:(JSONArray)->Unit,
        onError:(String)->Unit
    ) {
        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    val outerArray = JSONArray(response)

                    onSuccess(outerArray)
                }
                catch (ex:Exception)
                {
                    onError(response)
                }
            },
            { error ->
                onError(error.toString())
            })
        {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["wstoken"] = CORE_TOKEN
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
    override fun getTeacherUserByCourseGroupMoodle(context: Context,
                                                   courseid: String,
                                                   groupid:String,
                                                   roleid:String, onSuccess:(JSONArray)->Unit,
                                                   onError:(String)->Unit) {

        val mRequestQueue = Volley.newRequestQueue(context)
        val request = object : StringRequest(
            Method.POST, getMoodleServerUrl(),
            { response ->
                try{
                    val outerArray = JSONArray(response)

                    onSuccess(outerArray)
                }
                catch (ex:Exception)
                {
                    onError(response)
                }
            },
            { error ->
                onError(error.toString())
            })
        {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["wstoken"] = CORE_TOKEN
                params["wsfunction"] = "core_enrol_get_enrolled_users"
                params["moodlewsrestformat"] = "json"
                params["courseid"] = courseid
                params["options[0][name]"] = "roleid"
                params["options[0][value]"] = roleid
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