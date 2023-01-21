package com.uvpce.attendance_moodle_api_library

import android.content.Context
import java.math.BigInteger

interface iAttendanceRepository {
    fun getMoodleUserID(context: Context, username:String,callback: ServerCallback)
    fun getMoodleUserCoursesList(context: Context, username:String,callback: ServerCallback)
}