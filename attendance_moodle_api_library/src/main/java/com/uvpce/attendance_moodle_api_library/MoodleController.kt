package com.uvpce.attendance_moodle_api_library

class MoodleController {
    companion object {

        /**
         * @param MOODLEURL String
         * @param CORE_TOKEN String
         * @param ATTENDANCE_TOKEN String
         * @return AttendanceRepository Class Object
         */
        fun getAttendanceRepository(URL:String, CORE_TOKEN: String, ATTENDANCE_TOKEN:String): iAttendanceRepository {
            return AttendanceRepository(URL,CORE_TOKEN,ATTENDANCE_TOKEN)
        }
    }
}