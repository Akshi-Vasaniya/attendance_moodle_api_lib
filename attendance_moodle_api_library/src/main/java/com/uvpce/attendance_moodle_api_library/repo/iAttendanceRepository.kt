package com.uvpce.attendance_moodle_api_library.repo

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

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
    fun getUserInfoMoodle(username:String, onSuccess:(JSONArray)->Unit,
                          onError:(String)->Unit)

    /**
     * getFacultyInfoMoodle() use for get faculty info by user name.
     * @param Context Application Context
     * @param FacultyName String
     * @param callback ServerCallBack
     *
     * @return UserInfo - JSONArray
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/getFacultyInfoMoodle.txt">Sample Response</a>
     */
    fun getFacultyInfoMoodle(faculty_name: String,onSuccess:(JSONArray)->Unit,
                             onError:(String)->Unit)

    /**
     * getUserCoursesListMoodle() use for get courses list by user name.
     * @param Context Application Context
     * @param UserName String
     * @param callback ServerCallBack
     *
     * @return List of Courses - JSONArray
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/getUserCoursesListMoodle.txt">Sample Response</a>
     */
    fun getUserCoursesListMoodle(username:String,onSuccess:(JSONArray)->Unit,
                                 onError:(String)->Unit)

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
    fun createAttendanceMoodle(course_id:String, attendance_name:String, onSuccess:(JSONArray)->Unit,
                               onError:(String)->Unit)

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
    fun createSessionMoodle(course_id:String,attendance_id:String, session_time:String, duration:String, group_id:String, onSuccess:(JSONArray)->Unit,
                            onError:(String)->Unit)

    /**
     * getCourseGroupsMoodle() use for list Groups of specific Course.
     * @param Context Application Context
     * @param CourseID String
     * @param callback ServerCallBack
     *
     * @return GroupsInfo - JSONArray
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/getCourseGroupsMoodle.txt">Sample Response</a>
     */
    fun getCourseGroupsMoodle(course_id: String,onSuccess:(JSONArray)->Unit,
                              onError:(String)->Unit)

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
    fun sendMessageMoodle(user_id:String, attendance_id:String,course_id:String,
                          faculty_id:String, faculty_location:String, group_id:String, session_id:String,
                          start_time_of_attendance:String, end_time_of_attendance:String,
                          onSuccess:(JSONArray)->Unit,
                          onError:(String)->Unit)

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
    fun takeAttendanceMoodle(session_id:String,student_id:String,taken_by_id:String,status_id:String,status_set:String,onSuccess:(JSONArray)->Unit,
                             onError:(String)->Unit)

    /**
     * getSessionsListMoodle() use for list out all sessions of an attendance.
     * @param Context Application Context
     * @param AttendanceID String
     * @param callback ServerCallBack
     *
     * @return SessionsList - JSONArray
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/getSessionsListMoodle.txt">Sample Response</a>
     */
    fun getSessionsListMoodle(attendance_id:String,onSuccess:(JSONArray)->Unit,
                              onError:(String)->Unit)

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
    fun uploadFileMoodle(component:String,file_area:String,item_id:String,file_path:String,file_name:String,file_content:String,context_level:String,instanceid:String,onSuccess:(JSONArray)->Unit,
                         onError:(String)->Unit)

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
    fun updatePictureMoodle(draft_item_id:String,user_id:String,onSuccess:(JSONArray)->Unit,
                            onError:(String)->Unit)

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
    fun getMessageMoodle(user_id:String,type:String,read:String,onSuccess:(JSONObject)->Unit,
                         onError:(String)->Unit)

    /**
     * getCategoriesMoodle() use for get categories from moodle.
     * @param Context Application Context
     * @param callback ServerCallBack
     *
     * @return CategoriesInfo - JSONArray
     * @see <a href="https://github.com/dhavanikgithub/attendance_moodle_api_lib/blob/master/functions%20response/getCategoriesMoodle.txt">Sample Response</a>
     */
    fun getCategoriesMoodle(onSuccess:(JSONArray)->Unit,
                            onError:(String)->Unit)

    fun getCohortsMoodle(onSuccess:(JSONArray)->Unit,
                         onError:(String)->Unit)

    fun getCohortMembersMoodle(cohort_id:Int,onSuccess:(JSONArray)->Unit,
                               onError:(String)->Unit)

    fun getUserByFieldMoodle(field:String,value:ArrayList<String>,onSuccess:(JSONArray)->Unit,
                             onError:(String)->Unit)

    fun resolveImgURLMoodle(url:String,token:String):String

    fun getEnrolledUserByCourseGroupMoodle( courseid: String,groupid:String ,onSuccess:(JSONArray)->Unit,
                                           onError:(String)->Unit)

    fun getTeacherUserByCourseGroupMoodle(courseid: String, groupid:String, roleid:String, onSuccess:(JSONArray)->Unit,
                                          onError:(String)->Unit)
}