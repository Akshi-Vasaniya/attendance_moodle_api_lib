package com.uvpce.attendance_moodle_api_library

class MoodleController {
    companion object {
        fun getAttendanceRepository(URL:String,token:String): iAttendanceRepository {
            return AttendanceRepository(URL,token)
        }
    }
}