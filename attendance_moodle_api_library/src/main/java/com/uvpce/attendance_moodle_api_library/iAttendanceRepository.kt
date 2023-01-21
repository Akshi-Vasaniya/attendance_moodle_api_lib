package com.uvpce.attendance_moodle_api_library

import android.content.Context
import java.math.BigInteger

interface iAttendanceRepository {
    fun getMoodleUserID(context: Context, username:String, semester: Int,callback: ServerCallback)
    fun getMoodleUserCoursesList(context: Context, username:String, semester:Int,callback: ServerCallback)
    fun createAttendanceMoodle(context: Context, courseid:String,attendancename:String, semester: Int, callback: ServerCallback)
    fun createSessionMoodle(context: Context, courseid:String, attendancename:String, sessiontime:String, duration:String,groupid:String, semester: Int, callback: ServerCallback)

}