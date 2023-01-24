package com.uvpce.attendance_moodle_api_library


import org.json.JSONObject

interface ServerCall {

    /**
     * Call when response is success.
     * @param result JSONObject
     * @return None
     */
    fun onSuccess(result: JSONObject)

    /**
     * Call when response get error.
     * @param result String
     * @return None
     */
    fun onError(result: String)

}