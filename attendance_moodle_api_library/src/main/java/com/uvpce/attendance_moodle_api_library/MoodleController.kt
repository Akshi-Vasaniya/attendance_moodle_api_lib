package com.uvpce.attendance_moodle_api_library

class MoodleController {
    companion object {
        fun getAttendanceRepository(): iAttendanceRepository {
            return AttendanceRepository()
        }
    }
}