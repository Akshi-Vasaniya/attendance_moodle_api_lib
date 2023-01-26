package com.uvpce.attendance_moodle_api_library.model

import org.json.JSONObject

interface ModelBase {
    fun toJsonObject():JSONObject
}