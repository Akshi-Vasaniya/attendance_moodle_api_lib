package com.example.guniattendancefaculty.moodle.repo
import android.content.Context
import android.util.Log
import com.example.guniattendancefaculty.moodle.model.*
import com.uvpce.attendance_moodle_api_library.MoodleController
import com.uvpce.attendance_moodle_api_library.ServerCallback
import com.uvpce.attendance_moodle_api_library.iAttendanceRepository
import org.json.JSONArray

class MoodleRepository(val context:Context,val url:String,val core_token:String, val attendance_token:String, val file_upload_token:String) {
    private val attRepo: iAttendanceRepository = MoodleController.
    getAttendanceRepository(url, core_token,attendance_token,file_upload_token)

    fun getUserEnrolledCourseList(userName:String,onReceiveData:(List<MoodleCourse>)->Unit,onError:(String)->Unit){
        attRepo.getUserCoursesListMoodle(context,userName,object:ServerCallback{
            override fun onError(result: String) {
                onError(result)
            }
            override fun onSuccess(result: JSONArray) {
                val courseList = ArrayList<MoodleCourse>();
                for (i in 0 until result.length()){
                    courseList.add( MoodleCourse(
                        result.getJSONObject(i).getString("courseid"),
                        result.getJSONObject(i).getString("coursename"),
                        userName))
                }
                onReceiveData(courseList)
            }
        })
    }

    fun getStudentList(course:MoodleCourse,group:MoodleGroup,onReceiveData:(List<MoodleUserInfo>)->Unit,onError:(String)->Unit){
        attRepo.getEnrolledUserByCourseGroupMoodle(context,course.id,group.groupid,object :ServerCallback{
            override fun onError(result: String) {
                onError(result)
            }

            override fun onSuccess(result: JSONArray) {

                val userList = ArrayList<MoodleUserInfo>();
                for (i in 0 until result.length()){
                    userList.add(
                        MoodleUserInfo(course,group,
                            result.getJSONObject(i).getString("id"),
                            result.getJSONObject(i).getString("username"),
                            result.getJSONObject(i).getString("firstname"),
                            result.getJSONObject(i).getString("lastname"),
                            result.getJSONObject(i).getString("fullname"),
                            result.getJSONObject(i).getString("profileimageurl"))
                    )
                }
                onReceiveData(userList)

                Log.i("MoodleRepository", "onSuccess: "+result.toString(4))
            }

        })

    }


    fun getGroupInformation(course:MoodleCourse, onReceiveData: (List<MoodleGroup>) -> Unit, onError: (String) -> Unit){
        attRepo.getCourseGroupsMoodle(context,course.id,object:ServerCallback{
            override fun onError(result: String) {
                onError(result)
            }

            override fun onSuccess(result: JSONArray) {
                val groupList=ArrayList<MoodleGroup>();
                for (i in 0 until result.length()){
                    groupList.add(MoodleGroup(course,
                        result.getJSONObject(i).getString("groupid"),
                        result.getJSONObject(i).getString("groupname")))
                }
                onReceiveData(groupList)

            }
        })
    }


    fun getSessionInformation(course:MoodleCourse, onReceiveData: (List<MoodleSession>) -> Unit, onError: (String) -> Unit){
        attRepo.getSessionsListMoodle(context,"57",object:ServerCallback{
            override fun onError(result: String) {
                onError(result)
            }

            override fun onSuccess(result: JSONArray) {

                val i = 0

                Log.i("MoodleRepository", "onSuccess: "+result.toString(4))
                var sessionList=ArrayList<MoodleSession>();
                for (i in 0 until result.length()) {
                    sessionList.add(
                        MoodleSession(
                            course,
                            result.getJSONObject(i).getString("id"),
                            result.getJSONObject(i).getString("attendanceid"),
                            result.getJSONObject(i).getString("groupid"),
                            result.getJSONObject(i).getString("sessdate"),
                            result.getJSONObject(i).getString("duration")
                        )
                    )
                }
                onReceiveData(sessionList)
            }
        })
    }


    fun getFacultyInformation(userName: String, onReceiveData: (List<MoodleUserInfo>) -> Unit, onError: (String) -> Unit){
        attRepo.getFacultyInfoMoodle(context,userName,object:ServerCallback{
            override fun onError(result: String) {
                onError(result)
            }

            override fun onSuccess(result: JSONArray) {

                val i = 0
                Log.i("MoodleRepository", "onSuccess: "+result.toString(4))

//                var FacultyInfo=ArrayList<MoodleFacultyInfo>();
//                for (i in 0 until result.length()) {
//                    FacultyInfo.add(
//                        MoodleFacultyInfo(
//                        result.getJSONObject(i).getString("id"),
//                        result.getJSONObject(i).getString("username"),
//                        result.getJSONObject(i).getString("firstname"),
//                        result.getJSONObject(i).getString("lastname"),
//                        result.getJSONObject(i).getString("fullname"),
//                        result.getJSONObject(i).getString("profileimageurl")))
//                }
//                onReceiveData(FacultyInfo)
            }
        })
    }

//    fun getCourceGroup(cource_id:String,onReceiveData: (List<MoodleUser>) -> Unit,onError: (String) -> Unit){
//        attRepo.getUserInfoMoodle(context,cource_id,object:ServerCallback{
//            override fun onError(result: String) {
//                onError(result)
//            }
//            override fun onSuccess(result: JSONArray) {
//                val CourceGroupList=ArrayList<MoodleUser>();
//                for (i in 0 until result.length()){
//                    CourceGroupList.add(MoodleUser(result.getJSONObject(i).getString("id"),
//                        result.getJSONObject(i).getString("username"),
//                        result.getJSONObject(i).getString("firstname"),
//                        result.getJSONObject(i).getString("lastname"),
//                        result.getJSONObject(i).getString("fullname"),
//                        result.getJSONObject(i).getString("profileimageurl")))
//                }
//                onReceiveData(CourceGroupList)
//            }
//        })
//    }



}