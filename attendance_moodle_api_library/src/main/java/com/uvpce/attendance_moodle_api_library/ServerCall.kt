package com.uvpce.attendance_moodle_api_library

import org.json.JSONArray

interface ServerCall {

    /**
     * Call when response is success.
     * @param result JSONArray
     * @return None
     */
    fun onSuccess(result: String)

    /**
     * Call when response get error.
     * @param result String
     * @return None
     */
    fun onError(result: String)

}