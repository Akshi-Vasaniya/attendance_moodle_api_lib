package com.uvpce.attendance_moodle_api_library

class MoodleController {
    companion object {
        fun getAttendanceRepository(URL:String): iAttendanceRepository {
            return AttendanceRepository(URL)
        }
    }
}