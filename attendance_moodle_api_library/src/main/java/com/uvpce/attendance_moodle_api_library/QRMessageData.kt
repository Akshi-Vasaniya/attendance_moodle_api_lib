package com.uvpce.attendance_moodle_api_library

import org.json.JSONObject

class QRMessageData(var attendance_id:Int,
                    var sessionID:Int,
                    var session_start_time:Int,
                    var session_end_time:Int,
                    var session_duration:Int,
                    var faculty_userId:Int,
                    var courseid:Int,
                    var course_name:String,
                    var groupid:Int,
                    var group_name:String,
                    var faculty_loc: (Int, Int) -> Unit = { latitude:Int, longitude:Int -> },
                    var attendance_start_date:Int,
                    var attendance_end_date:Int,
                    var attendance_duration:Int) {
    companion object{
        var data = JSONObject()
    }
    init{
        data.put("attendance_id",this.attendance_id)
        data.put("sessionID",this.sessionID)
        data.put("session_start_time",this.session_start_time)
        data.put("session_end_time",this.session_end_time)
        data.put("session_duration",this.session_duration)
        data.put("faculty_userId",this.faculty_userId)
        data.put("courseid",this.courseid)
        data.put("course_name",this.course_name)
        data.put("groupid",this.groupid)
        data.put("faculty_loc",this.faculty_loc)
        data.put("attendance_start_date",this.attendance_start_date)
        data.put("attendance_end_date",this.attendance_end_date)
        data.put("group_name",this.group_name)
        data.put("attendance_duration",this.attendance_duration)
    }
}