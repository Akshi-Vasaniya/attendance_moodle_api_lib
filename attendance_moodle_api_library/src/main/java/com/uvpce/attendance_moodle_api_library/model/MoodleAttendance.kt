package com.uvpce.attendance_moodle_api_library.model

import org.json.JSONObject

class MoodleAttendance(val course: MoodleCourse, val attendanceId:String, val attendanceName:String):ModelBase {
    companion object{
        fun fromJsonObject(jsonString: String):MoodleAttendance {
            val jsonObject = JSONObject(jsonString)
            return MoodleAttendance(
                MoodleCourse.fromJsonObject(jsonObject.getString("course")),
                jsonObject.getString("attendanceId"),
                jsonObject.getString("attendanceName")
            )
        }
    }

    override fun toJsonObject():JSONObject{
        val json = JSONObject()
        json.put("course",course.toJsonObject())
        json.put("attendanceId",attendanceId)
        json.put("attendanceName",attendanceName)
        return json
    }
    override fun toString(): String {
        return toJsonObject().toString(4)
    }
}