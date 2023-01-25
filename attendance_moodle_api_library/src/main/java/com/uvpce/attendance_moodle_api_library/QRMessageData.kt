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
                    var faculty_loc_lat: Int,
                    var faculty_loc_long: Int,
                    var attendance_start_date:Int,
                    var attendance_end_date:Int,
                    var attendance_duration:Int) {
    companion object{
        var attendance_id:Int=0
        var sessionID:Int=0
        var session_start_time:Int=0
        var session_end_time:Int=0
        var session_duration:Int=0
        var faculty_userId:Int=0
        var courseid:Int=0
        var course_name:String="null"
        var groupid:Int=0
        var group_name:String="null"
        var faculty_loc_lat: Int=0
        var faculty_loc_long: Int=0
        var attendance_start_date:Int=0
        var attendance_end_date:Int=0
        var attendance_duration:Int=0
    }
    init{
        QRMessageData.attendance_id=this.attendance_id
        QRMessageData.sessionID=this.sessionID
        QRMessageData.session_start_time=this.session_start_time
        QRMessageData.session_end_time=this.session_end_time
        QRMessageData.session_duration=this.session_duration
        QRMessageData.faculty_userId=this.faculty_userId
        QRMessageData.courseid=this.courseid
        QRMessageData.course_name=this.course_name
        QRMessageData.groupid=this.groupid
        QRMessageData.group_name=this.group_name
        QRMessageData.attendance_start_date=this.attendance_start_date
        QRMessageData.attendance_end_date=this.attendance_end_date
        QRMessageData.attendance_duration=this.attendance_duration
        QRMessageData.faculty_loc_lat=this.faculty_loc_lat
        QRMessageData.faculty_loc_long=this.faculty_loc_long
    }
}