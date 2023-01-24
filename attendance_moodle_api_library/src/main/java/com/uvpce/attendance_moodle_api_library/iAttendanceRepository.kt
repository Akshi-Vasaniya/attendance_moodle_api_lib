package com.uvpce.attendance_moodle_api_library

import android.content.Context
import java.math.BigInteger

interface iAttendanceRepository {

    /**
     * getUserInfoMoodle() use for get user id by user name.
     * @param Context Application Context
     * @param Username String
     * @param callback ServerCallBack
     *
     * @return User ID - JSONArray (return Only single key-value pair)
     * @(key = id, value = userid)
     */
    fun getUserInfoMoodle(context: Context, username:String,callback: ServerCallback)

    fun getFacultyInfoMoodle(context: Context,faculty_name: String,serverCallback: ServerCallback)

    /**
     * getUserCoursesListMoodle() use for get courses list by user name.
     * @param Context Application Context
     * @param Username String
     * @param callback ServerCallBack
     *
     * @return List of Courses - JSONArray (return Multiple key-value pair)
     * @{{"Course1Name":"Course1ID",....."CourseN":"CourseNID"}}
     */
    fun getUserCoursesListMoodle(context: Context, username:String,callback: ServerCallback)


    fun createAttendanceMoodle(context: Context, course_id:String, attandance_name:String, callback: ServerCallback)

    fun createSessionMoodle(context: Context, course_id:String,attandance_name:String, session_time:String, duration:String, group_id:String, callback: ServerCallback)

    fun getCourseGroupsMoodle(context: Context,course_id: String,callback: ServerCallback)

    fun sendMessangeMoodle(context: Context, user_id:String, attendance_id:String,course_id:String,faculty_id:String, faculty_location:String, group_id:String, session_id:String, start_time_of_attendance:String, end_time_of_attendance:String, callback: ServerCallback)

    fun takeAttendanceMoodle(context: Context, session_id:String,student_id:String,taken_by_id:String,status_id:String,status_set:String,callback: ServerCallback)

    fun getSessionsListMoodle(context: Context,attendance_id:String,callback: ServerCallback)

    fun uploadFileMoodle(context: Context,component:String,file_area:String,item_id:String,file_path:String,file_name:String,file_content:String,context_level:String,instanceid:String,callback: ServerCallback)

    fun updatePictureMoodle(context: Context,draft_item_id:String,user_id:String,callback: ServerCallback)

    fun getMessageMoodle(context: Context,user_id:String,type:String,read:String,callback: ServerCall)

    fun getCategoriesMoodle(context: Context,callback: ServerCallback)

}