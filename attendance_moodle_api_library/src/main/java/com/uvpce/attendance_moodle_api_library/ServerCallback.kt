package com.uvpce.attendance_moodle_api_library

import org.json.JSONArray
import org.json.JSONObject

interface ServerCallback {
    fun onSuccess(result: JSONArray)
    fun onError(result: String)
}