package com.uvpce.attendance_moodle_api_library.model

import org.json.JSONArray

class QRMessageData {
    companion object{
        var attendance_id:Int=57
        val sessionData = JSONArray()
        var session_start_time:Int=0
        var session_end_time:Int=0
        var session_duration:Int=0
        var faculty_userId:Int=0
        var courseid:Int=0
        var attendanceid_by_course= mutableMapOf<String, String>()
        var course_name:String="null"
        var groupid:Int=0
        var group_name:String="null"
        var faculty_loc_lat: Int=0
        var faculty_loc_long: Int=0
        var attendance_start_date:Int=0
        var attendance_end_date:Int=0
        var attendance_duration:Int=0
    }
}