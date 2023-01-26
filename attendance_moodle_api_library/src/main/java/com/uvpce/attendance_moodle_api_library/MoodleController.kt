package com.uvpce.attendance_moodle_api_library

import com.uvpce.attendance_moodle_api_library.repo.AttendanceRepository
import com.uvpce.attendance_moodle_api_library.repo.iAttendanceRepository

class MoodleController {
    companion object {

        /**
         * @param MOODLE_URL String
         * @param CORE_TOKEN String
         * @param ATTENDANCE_TOKEN String
         * @param UPLOAD_FILE_TOKEN String
         * @return AttendanceRepository Class Object
         */
        fun getAttendanceRepository(MOODLE_URL:String, CORE_TOKEN: String, ATTENDANCE_TOKEN:String, UPLOAD_FILE_TOKEN:String): iAttendanceRepository {
            return AttendanceRepository(MOODLE_URL,CORE_TOKEN,ATTENDANCE_TOKEN,UPLOAD_FILE_TOKEN)
        }
    }
}