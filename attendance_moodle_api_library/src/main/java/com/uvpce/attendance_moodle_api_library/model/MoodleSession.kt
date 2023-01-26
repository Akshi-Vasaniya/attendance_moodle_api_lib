package com.uvpce.attendance_moodle_api_library.model

import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MoodleSession(val attendance: MoodleAttendance,
                    val course: MoodleCourse,
                    val group: MoodleGroup,
                    val sessionId:String,
                    val description:String,
                    sessionStartDateString:String,
                    val duration:String): ModelBase
{
    val statusList = ArrayList<MoodleSessionStatus>()
    val durationMinutes= duration.toLong() / 60;
    val sessionStartDate = sessionStartDateString.trim().toLong()*1000
    val sessionEndDate = (sessionStartDateString.toLong() + duration.toLong())*1000
    fun getPresentStatusId():MoodleSessionStatus{
        val returnId = statusList[0]
        for(i in 0 until statusList.size){
            if(statusList[i].name.uppercase(Locale.ROOT) == "P")
                return statusList[i]
        }
        return returnId
    }
    companion object{
        fun fromJsonObject(jsonString: String): MoodleSession {
            val jsonObject = JSONObject(jsonString)
            val jsonArray = jsonObject.getJSONArray("statusList")
            val course = MoodleCourse.fromJsonObject(jsonObject.getString("course"))
            val obj = MoodleSession(
                MoodleAttendance.fromJsonObject(jsonObject.getString("attendance")),
                course,
                MoodleGroup.fromJsonObject(course,jsonObject.getString("group")),
                jsonObject.getString("sessionId"),
                jsonObject.getString("description"),
                jsonObject.getString("sessionStartDateString"),
                jsonObject.getString("duration")
            )
            for(i in 0 until jsonArray.length()){
                obj.statusList.add(MoodleSessionStatus.fromJsonObject(obj,jsonArray[i].toString()))
            }
            return obj
        }
    }
    override fun toJsonObject(): JSONObject {
        val json = JSONObject()
        val jsonArray = JSONArray()
        for(i in 0 until statusList.size){
            jsonArray.put(statusList[i].toJsonObject())
        }
        json.put("statusList",jsonArray)
        json.put("attendance",attendance.toJsonObject())
        json.put("course",course.toJsonObject())
        json.put("group",group.toJsonObject())
        json.put("sessionId",sessionId)
        json.put("description",description)
        json.put("sessionStartDateString",sessionStartDate)
        json.put("duration",duration)
        return json
    }

    override fun toString(): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a")
        return "\nid=$sessionId \nattendanceid=${attendance.attendanceId} \ngroupid=${group.groupid} \nsessionStartDate=${simpleDateFormat.format(sessionStartDate)} \nsessionEndDate=${simpleDateFormat.format(sessionEndDate)} \nduration=$durationMinutes mins\n"
    }
}