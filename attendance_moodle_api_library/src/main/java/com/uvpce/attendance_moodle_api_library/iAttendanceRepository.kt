package com.uvpce.attendance_moodle_api_library

import android.content.Context
import java.math.BigInteger

interface iAttendanceRepository {
    fun getMoodleUserID(context: Context, CORE_TOKEN:String, username:String,callback: ServerCallback)
    fun getMoodleUserCoursesList(context: Context, CORE_TOKEN:String,username:String,callback: ServerCallback)
}