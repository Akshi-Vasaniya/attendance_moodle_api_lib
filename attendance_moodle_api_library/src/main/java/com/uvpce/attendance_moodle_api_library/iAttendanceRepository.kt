package com.uvpce.attendance_moodle_api_library

import android.content.Context
import java.math.BigInteger

interface iAttendanceRepository {

    /**
     * getMoodleUserID() use for get user id by user name.
     * @param Context Application Context
     * @param Username String
     * @param callback ServerCallBack
     *
     * @return User ID - JSONArray (return Only single key-value pair)
     * @(key = id, value = userid)
     */
    fun getMoodleUserID(context: Context, username:String,callback: ServerCallback)

    fun getMoodleFacultyInfo(context: Context,faculty_name: String,serverCallback: ServerCallback)

    /**
     * getMoodleUserCoursesList() use for get courses list by user name.
     * @param Context Application Context
     * @param Username String
     * @param callback ServerCallBack
     *
     * @return List of Courses - JSONArray (return Multiple key-value pair)
     * @{{"Course1Name":"Course1ID",....."CourseN":"CourseNID"}}
     */
    fun getMoodleUserCoursesList(context: Context, username:String,callback: ServerCallback)


    fun createAttendanceMoodle(context: Context, course_id:String, attandance_name:String, callback: ServerCallback)

    fun createSessionMoodle(context: Context, course_id:String,attandance_name:String, session_time:String, duration:String, group_id:String, callback: ServerCallback)

    fun getCourseGroups(context: Context,course_id: String,callback: ServerCallback)

    fun sendMessangeMoodle(context: Context, user_id:String, faculty_name:String, faculty_location:String, session_id:String, start_time:String, end_time:String, callback: ServerCallback)

    fun takeAttendance(context: Context, session_id:String,student_id:String,taken_by_id:String,status_id:String,status_set:String,callback: ServerCallback)

    fun getSessionsList(context: Context,attendance_id:String,callback: ServerCallback)

    fun uploadFileMoodle(context: Context,component:String,file_area:String,item_id:String,file_path:String,file_name:String,file_content:String,context_level:String,instanceid:String,callback: ServerCallback)

    fun updatePicture(context: Context,draft_item_id:String,user_id:String,callback: ServerCallback)

//    fun getMessageMoodle(context: Context,user_id:String,type:String,read:String,callback: ServerCallback)

}