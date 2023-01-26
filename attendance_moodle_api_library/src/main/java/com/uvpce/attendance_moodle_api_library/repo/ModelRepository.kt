package com.uvpce.attendance_moodle_api_library.repo
import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.guniattendancefaculty.moodle.model.*
import com.uvpce.attendance_moodle_api_library.model.*
import com.uvpce.attendance_moodle_api_library.util.BitmapUtils
import org.json.JSONArray
import org.json.JSONException
import java.nio.file.Path
import java.nio.file.Paths

class ModelRepository(
    private val context:Context,
    private val moodleUrl:String) {
    private val attRepo = AttendanceRepository(context,moodleUrl, ClientAPI.coreToken,ClientAPI.attendanceToken,ClientAPI.fileUploadToken)
    private val TAG = "ModelRepository"
    fun isStudentRegisterForFace(enrollmentNo:String, onReceive:(Boolean)->Unit, onError:(String)->Unit){
        try{
            attRepo.getUserInfoMoodle(enrollmentNo, onSuccess =  { result->
                try {
                    var fetchedProfileURL = ""
                    //Enrollment exists.
                    (0 until result.length()).forEach {
                        val item = result.getJSONObject(it)
                        fetchedProfileURL = item.get("profileimageurl").toString()
                        Log.i("fetched", fetchedProfileURL)
                    }

                    val finalFetchedProfileURL = BitmapUtils.finalizeURL(
                        fetchedProfileURL,
                        "8d29dd97dd7c93b0e3cdd43d4b797c87"
                    )
                    Log.i(TAG, "finalFetchedProfileURL:${finalFetchedProfileURL}")

                    val convertedfetchedProfileImage =
                        BitmapUtils.convertUrlToBase64(finalFetchedProfileURL)
                    Log.i(TAG, "convertedfetchedProfileImage:$convertedfetchedProfileImage")

                    val converteduserDefaultPic =
                        BitmapUtils.convertUrlToBase64(ClientAPI.getDefaultUserPictureUrl(moodleUrl))
                    Log.i(TAG, "converteduserDefaultPic:$converteduserDefaultPic")

                    if(convertedfetchedProfileImage == converteduserDefaultPic){
                        onReceive(true)
                    }
                    else{
                        //OPEN CAMERA FOR ATTENDANCE
                        onReceive(false)
                    }
                } catch (e: Exception){
                    Log.i("Exception", "$e")
                    onError("Error:$e")
                }
            },onError={result->
                onError(result)
                //Enrollment does not exists.
            }
            )
        }catch (e:Exception){
            Log.i("Exception", "$e")
            onError("Error:$e")
        }
    }
    fun getStudentInfo(enrollmentNo:String, onReceive:(BaseUserInfo)->Unit, onError:(String)->Unit){
        try{
        attRepo.getUserInfoMoodle( enrollmentNo, onSuccess={result->
                val item = result.getJSONObject(0)
                val userid = item.get("id").toString()
                val firstname = item.get("firstname").toString()
                val lastname = item.get("lastname").toString()
                val fullname = item.get("fullname").toString()
                val emailaddr = item.get("email").toString()
                val image = item.get("profileimageurl").toString()
                onReceive(BaseUserInfo(userid,enrollmentNo,firstname,lastname,fullname,emailaddr,image))
            },onError={

            }

        )
        }catch (e:Exception){
            Log.i("Exception", "$e")
            onError("Error:$e")
        }
    }
    fun uploadStudentPicture(userid:String, curImageUri: Uri, onReceive: (JSONArray) -> Unit, onError: (String) -> Unit) {
        try {
            //Update the selected photo in moodle
            val bitmap =
                context.let { it1 ->
                    BitmapUtils.getBitmapFromUri(
                        it1.contentResolver,
                        curImageUri
                    )
                }
            val bitmapStr = bitmap.let { it1 -> BitmapUtils.bitmapToString(it1) }

            //Create regex to get the filename from curImageUri
            val path: Path = Paths.get(curImageUri.toString())
            val filename: String = path.fileName.toString()
            //Uploading the correct chosen pic
            attRepo.uploadFileMoodle(

                "user",
                "draft",
                "0",
                "/",
                filename,
                "$bitmapStr",
                "user",
                "2",
                onSuccess = { result ->
                    Log.i(TAG, "Successfully uploaded the file:$result")
                    val item = result.getJSONObject(0)
                    //val contextid = item.get("contextid").toString()
                    val draftitemid = item.get("itemid").toString()
                    //Updated the uploaded picture.
                    attRepo.updatePictureMoodle(
                        draftitemid,
                        userid,
                        onSuccess = { result1 ->
                            onReceive(result1)
                        },
                        onError = { result2 ->
                            onError(result2)
                        }
                    )
                }, onError = { result ->
                    onError(result)
                }
            )
        } catch (e: Exception) {
            Log.i("Exception", "$e")
            onError("Error:$e")
        }
    }
    fun getCourseListEnrolledByUser(userName:String, onReceiveData:(List<MoodleCourse>)->Unit, onError:(String)->Unit) {
        try {
            attRepo.getUserCoursesListMoodle(userName, onError = { result ->
                onError(result)
            }, onSuccess = { result ->
                val courseList = ArrayList<MoodleCourse>();
                for (i in 0 until result.length()) {
                    courseList.add(
                        MoodleCourse(
                            result.getJSONObject(i).getString("courseid"),
                            result.getJSONObject(i).getString("coursename"),
                            userName
                        )
                    )
                }
                onReceiveData(courseList)
            }
            )
        } catch (e: Exception) {
            Log.i("Exception", "$e")
            onError("Error:$e")
        }
    }
    fun getStudentList(course: MoodleCourse, group: MoodleGroup, onReceiveData:(List<MoodleUserInfo>)->Unit, onError:(String)->Unit){
        try{
        attRepo.getEnrolledUserByCourseGroupMoodle(course.id,group.groupid,
            onError={result->
                onError(result)
            }, onSuccess={result->

                val userList = ArrayList<MoodleUserInfo>();
                for (i in 0 until result.length()){
                    userList.add(
                        MoodleUserInfo(course,group,
                            result.getJSONObject(i).getString("id"),
                            result.getJSONObject(i).getString("username"),
                            result.getJSONObject(i).getString("firstname"),
                            result.getJSONObject(i).getString("lastname"),
                            result.getJSONObject(i).getString("fullname"),
                            result.getJSONObject(i).getString("email"),
                            result.getJSONObject(i).getString("profileimageurl")
                        )
                    )
                }
                onReceiveData(userList)

                Log.i("MoodleRepository", "onSuccess: "+result.toString(4))
            }
        )
    } catch (e: Exception) {
        Log.i("Exception", "$e")
        onError("Error:$e")
    }
    }
    fun getGroupList(course: MoodleCourse, onReceiveData: (List<MoodleGroup>) -> Unit, onError: (String) -> Unit) {
        try {
            attRepo.getCourseGroupsMoodle(course.id,
                onError = { result ->
                    onError(result)
                }, onSuccess = { result ->
                    val groupList = ArrayList<MoodleGroup>();
                    for (i in 0 until result.length()) {
                        groupList.add(
                            MoodleGroup(
                                course,
                                result.getJSONObject(i).getString("groupid"),
                                result.getJSONObject(i).getString("groupname")
                            )
                        )
                    }
                    course.groupList.addAll(groupList)
                    onReceiveData(groupList)
                }
            )
        } catch (e: Exception) {
            Log.i("Exception", "$e")
            onError("Error:$e")
        }
    }
    fun getSessionInformation(course: MoodleCourse, attendance: MoodleAttendance, group: MoodleGroup,
                              onReceiveData: (List<MoodleSession>) -> Unit,
                              onError: (String) -> Unit) {
        try {
            attRepo.getSessionsListMoodle(attendance.attendanceId, onError = { result ->
                onError(result)
            }, onSuccess = { result ->
                Log.i("MoodleRepository", "onSuccess: " + result.toString(4))
                val sessionList = ArrayList<MoodleSession>();
                for (i in 0 until result.length()) {
                    try {
                        val groupId = result.getJSONObject(i).getInt("groupid")
                        if (groupId == group.groupid.toInt()) {
                            val obj = MoodleSession(
                                attendance, course, group,
                                result.getJSONObject(i).getString("id"),
                                result.getJSONObject(i).getString("description"),
                                result.getJSONObject(i).getString("sessdate"),
                                result.getJSONObject(i).getString("duration")
                            )
                            val statusList = result.getJSONObject(i).getJSONArray("statuses")
                            for (j in 0 until statusList.length()) {
                                obj.statusList.add(
                                    MoodleSessionStatus(
                                        session = obj,
                                        id = statusList.getJSONObject(j).getString("id"),
                                        name = statusList.getJSONObject(j).getString("acronym"),
                                        description = statusList.getJSONObject(j)
                                            .getString("description"),
                                        grade = statusList.getJSONObject(j).getInt("grade")
                                    )
                                )
                            }
                            sessionList.add(obj)
                        }
                    } catch (e: JSONException) {
                        Log.e(TAG, "getSessionInformation: $e", e)
                        onError("Error: $e")
                    }
                }
                onReceiveData(sessionList)
            }
            )
        } catch (e: Exception) {
            Log.i("Exception", "$e")
            onError("Error:$e")
        }
    }
    fun getFacultyInformation(userName: String, onReceiveData: (List<MoodleUserInfo>) -> Unit, onError: (String) -> Unit){
        try{
        attRepo.getFacultyInfoMoodle(userName,onError={result->
                onError(result)
            },onSuccess={result->

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
        )
    } catch (e: Exception) {
        Log.i("Exception", "$e")
        onError("Error:$e")
    }
    }
    fun getAttendance(course: MoodleCourse,
                      onReceiveData: (MoodleAttendance) -> Unit,
                      onError: (String) -> Unit) {
        try {
            val courseAttendance = ClientAPI.courseAttendaceMap.getJSONObject(course.id)
            val attendanceName = courseAttendance.getString("name")
            val attendanceId = courseAttendance.getString("id")
            onReceiveData(MoodleAttendance(course, attendanceId, attendanceName))
        } catch (e: Exception) {
            Log.i("Exception", "$e")
            onError("Error:$e")
        }
    }

    /**
     * This method returned data is used to generate QR Code image for faculty app.
     */
    fun getQRDataString(data:QRMessageData,
                        onSuccess:(String)->Unit,
                        onError:(String)->Unit){
        try{
        QRMessageData.getQRMessageString(data,onSuccess,onError)
        } catch (e: Exception) {
            Log.i("Exception", "$e")
            onError("Error:$e")
        }
    }
    fun takePresentAttendance(scannedQRStringResponse:String,
                       enrollmentNo: String,
                       onSuccess:(JSONArray)->Unit,
                       onError:(String)->Unit){
        try{
        QRMessageData.getQRMessageObject(scannedQRStringResponse,
            onSuccess={
                        val status = it.session.getPresentStatusId()
                        attRepo.takeAttendanceMoodle(
                          session_id = it.session.sessionId,
                          student_id = enrollmentNo,
                          taken_by_id = it.attendanceByFacultyId,
                          status_id = status.id,
                          status_set = status.name,
                          onSuccess = onSuccess,
                          onError = onError
                      )
            },
            onError={
                onError(it)
            })
        } catch (e: Exception) {
            Log.i("Exception", "$e")
            onError("Error:$e")
        }
    }
    fun createAttendance(course:MoodleCourse,attendanceName:String,
                         onSuccess:(MoodleAttendance)->Unit,
                         onError:(String)->Unit){
        try{
        attRepo.createAttendanceMoodle(course.id,attendanceName,
            onSuccess = {
            try {
                onSuccess(MoodleAttendance(course, it.getJSONObject(0).getString("id"),attendanceName))
            }catch (e:Exception){
                Log.e(TAG, "createAttendance: $e", e)
                onError("Error:$e")
            }
        },
        onError={
            onError(it)
        })
        } catch (e: Exception) {
            Log.i("Exception", "$e")
            onError("Error:$e")
        }
    }
    fun login(recievedMoodleUsername:String,
              recievedmoodlePassword:String,
              onSuccess:(Boolean)->Unit,
              onError:(String)->Unit){
        try{
            attRepo.login(recievedMoodleUsername,recievedmoodlePassword,
            onSuccess={
                onSuccess(it)
            },onError={
                onError(it)
                })

        }catch (e:Exception){
            onError("Error Login:$e")
            Log.e(TAG, "login: $e", e)
        }
    }
}