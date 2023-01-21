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
}