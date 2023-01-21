package com.uvpce.attendance_moodle_api_library

import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.math.BigInteger

class AttendanceRepository(val URL:String):iAttendanceRepository{
    override fun getMoodleUserID(context: Context, CORE_TOKEN:String, username: String,callback: ServerCallback) {

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

    override fun getMoodleUserCoursesList(context: Context, CORE_TOKEN:String, username: String,callback: ServerCallback) {
        val mRequestQueue = Volley.newRequestQueue(context)
        getMoodleUserID(context,CORE_TOKEN,username,object :ServerCallback{
            override fun onSuccess(result: JSONArray) {
                var userid= result.getJSONObject(0).getString("id")
                val request = object : StringRequest(
                    Method.POST, URL,
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
}