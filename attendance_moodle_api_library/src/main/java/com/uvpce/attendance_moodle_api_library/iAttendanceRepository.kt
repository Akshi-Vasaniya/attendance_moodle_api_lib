package com.uvpce.attendance_moodle_api_library

import android.content.Context
import java.math.BigInteger

interface iAttendanceRepository {

    /**
     * getUserInfoMoodle() use for get user info by user name.
     * @param Context Application Context
     * @param UserName String
     * @param callback ServerCallBack
     *
     * @return UserInfo - JSONArray
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/getFacultyInfoMoodle.txt">Sample Response</a>
     */
    fun getUserInfoMoodle(context: Context, username:String,callback: ServerCallback)

    /**
     * getFacultyInfoMoodle() use for get faculty info by user name.
     * @param Context Application Context
     * @param FacultyName String
     * @param callback ServerCallBack
     *
     * @return UserInfo - JSONArray
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/getFacultyInfoMoodle.txt">Sample Response</a>
     */
    fun getFacultyInfoMoodle(context: Context,faculty_name: String,serverCallback: ServerCallback)

    /**
     * getUserCoursesListMoodle() use for get courses list by user name.
     * @param Context Application Context
     * @param UserName String
     * @param callback ServerCallBack
     *
     * @return List of Courses - JSONArray
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/getUserCoursesListMoodle.txt">Sample Response</a>
     */
    fun getUserCoursesListMoodle(context: Context, username:String,callback: ServerCallback)

    /**
     * createAttendanceMoodle() use for create an attendance.
     * @param Context Application Context
     * @param CourseID String
     * @param AttendanceName String
     * @param callback ServerCallBack
     *
     * @return attendance id - JSONArray
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/createAttendanceMoodle.txt">Sample Response</a>
     */
    fun createAttendanceMoodle(context: Context, course_id:String, attendance_name:String, callback: ServerCallback)

    /**
     * createSessionMoodle() use for create session inside attendance.
     * @param Context Application Context
     * @param CourseID String
     * @param AttendanceID String
     * @param SessionTime String
     * @param Duration String
     * @param GroupID  String
     * @param callback ServerCallBack
     *
     * @return session id - JSONArray
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/createSessionMoodle.txt">Sample Response</a>
     */
    fun createSessionMoodle(context: Context, course_id:String,attendance_id:String, session_time:String, duration:String, group_id:String, callback: ServerCallback)

    /**
     * getCourseGroupsMoodle() use for list Groups of specific Course.
     * @param Context Application Context
     * @param CourseID String
     * @param callback ServerCallBack
     *
     * @return GroupsInfo - JSONArray
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/getCourseGroupsMoodle.txt">Sample Response</a>
     */
    fun getCourseGroupsMoodle(context: Context,course_id: String,callback: ServerCallback)

    /**
     * sendMessageMoodle() use for sending a message on Moodle.
     * @param Context Application Context
     * @param UserID String
     * @param AttendanceID String
     * @param CourseID String
     * @param FacultyID String
     * @param FacultyLocation String
     * @param GroupID String
     * @param SessionID String
     * @param StartTimeOfAttendance String
     * @param EndTimeOfAttendance String
     * @param callback ServerCallBack
     *
     * @return MessageInfo - JSONArray
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/sendMessangeMoodle.txt">Sample Response</a>
     */
    fun sendMessageMoodle(context: Context, user_id:String, attendance_id:String,course_id:String,faculty_id:String, faculty_location:String, group_id:String, session_id:String, start_time_of_attendance:String, end_time_of_attendance:String, callback: ServerCallback)

    /**
     * takeAttendanceMoodle() use for mark attendance on moodle.
     * @param Context Application Context
     * @param SessionID String
     * @param StudentID String
     * @param TakenByID String
     * @param StatusID String
     * @param SetStatusID String
     * @param callback ServerCallBack
     *
     * @return Null
     */
    fun takeAttendanceMoodle(context: Context, session_id:String,student_id:String,taken_by_id:String,status_id:String,status_set:String,callback: ServerCallback)

    /**
     * getSessionsListMoodle() use for list out all sessions of an attendance.
     * @param Context Application Context
     * @param AttendanceID String
     * @param callback ServerCallBack
     *
     * @return SessionsList - JSONArray
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/getSessionsListMoodle.txt">Sample Response</a>
     */
    fun getSessionsListMoodle(context: Context,attendance_id:String,callback: ServerCallback)

    /**
     * uploadFileMoodle() use for upload file on moodle.
     * @param Context Application Context
     * @param Component String
     * @param FileArea String
     * @param ItemID String
     * @param FilePath String
     * @param FileName String
     * @param FileContent String
     * @param ContextLevel String
     * @param InstanceID String
     * @param callback ServerCallBack
     *
     * @return UploadFileInfo - JSONArray
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/uploadFileMoodle.txt">Sample Response</a>
     */
    fun uploadFileMoodle(context: Context,component:String,file_area:String,item_id:String,file_path:String,file_name:String,file_content:String,context_level:String,instanceid:String,callback: ServerCallback)

    /**
     * updatePictureMoodle() use for use for update profile picture in moodle.
     * @param Context Application Context
     * @param DraftItemID String
     * @param UserID String
     * @param callback ServerCallBack
     *
     * @return UpdatedPictureInfo - JSONArray
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/updatePictureMoodle.txt">Sample Response</a>
     */
    fun updatePictureMoodle(context: Context,draft_item_id:String,user_id:String,callback: ServerCallback)

    /**
     * getMessageMoodle() use for get message from moodle.
     * @param Context Application Context
     * @param UserID String
     * @param Type String
     * @param Read String
     * @param callback ServerCall
     *
     * @return Message - JSONObject
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/getMessageMoodle.txt">Sample Response</a>
     */
    fun getMessageMoodle(context: Context,user_id:String,type:String,read:String,callback: ServerCall)

    /**
     * getCategoriesMoodle() use for get categories from moodle.
     * @param Context Application Context
     * @param callback ServerCallBack
     *
     * @return CategoriesInfo - JSONArray
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/getCategoriesMoodle.txt">Sample Response</a>
     */
    fun getCategoriesMoodle(context: Context,callback: ServerCallback)

    fun getCohorts(context: Context,callback: ServerCallback)

}