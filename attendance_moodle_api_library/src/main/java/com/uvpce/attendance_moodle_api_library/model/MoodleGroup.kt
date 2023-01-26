package com.uvpce.attendance_moodle_api_library.model

import org.json.JSONObject

class MoodleGroup(val course: MoodleCourse, val groupid:String, val groupName:String): ModelBase {
    companion object{
        fun fromJsonObject(course: MoodleCourse, jsonString: String): MoodleGroup {
            val jsonObject = JSONObject(jsonString)
            return MoodleGroup(
                course,
                jsonObject.getString("attendanceId"),
                jsonObject.getString("attendanceName")
            )
        }
    }
    override fun toJsonObject(): JSONObject {
        val json = JSONObject()
        json.put("groupid",groupid)
        json.put("groupName",groupName)
        return json
    }

    override fun toString(): String {
        return "\ngroupid=$groupid \ngroupname=$groupName"
    }
}