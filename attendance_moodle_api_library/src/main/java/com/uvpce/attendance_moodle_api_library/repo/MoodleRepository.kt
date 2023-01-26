package com.uvpce.attendance_moodle_api_library.repo
import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.guniattendancefaculty.moodle.model.*
import com.uvpce.attendance_moodle_api_library.MoodleController
import com.uvpce.attendance_moodle_api_library.util.BitmapUtils
import org.json.JSONArray
import java.nio.file.Path
import java.nio.file.Paths

class MoodleRepository(
    private val context:Context,
    private val moodleUrl:String) {
    private val attRepo: iAttendanceRepository = MoodleController.
    getAttendanceRepository(moodleUrl, ClientAPI.coreToken,ClientAPI.attendanceToken,ClientAPI.fileUploadToken)
    private val TAG = "MoodleRepository"

    fun isStudentRegisterForFace(enrollmentNo:String, onReceive:(Boolean)->Unit, onError:(String)->Unit){

        attRepo.getUserInfoMoodle(context, enrollmentNo, onSuccess =  { result->
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

    }
    fun getStudentInfo(enrollmentNo:String, onReceive:(BaseUserInfo)->Unit, onError:(String)->Unit){
        attRepo.getUserInfoMoodle( context, enrollmentNo, onSuccess={result->
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
    }
    fun uploadStudentPicture(userid:String, curImageUri: Uri, onReceive: (JSONArray) -> Unit, onError: (String) -> Unit) {
        //Update the selected photo in moodle
        val bitmap =
            context.let { it1 -> BitmapUtils.getBitmapFromUri(it1.contentResolver, curImageUri) }
        val bitmapStr = bitmap.let { it1 -> BitmapUtils.bitmapToString(it1) }

        //Create regex to get the filename from curImageUri
        val path: Path = Paths.get(curImageUri.toString())
        val filename: String = path.fileName.toString()
        //Uploading the correct chosen pic
        attRepo.uploadFileMoodle(
            context,
            "user",
            "draft",
            "0",
            "/",
            filename,
            "$bitmapStr",
            "user",
            "2",
            onSuccess={result->
                    Log.i(TAG, "Successfully uploaded the file:$result")
                    val item = result.getJSONObject(0)
                    //val contextid = item.get("contextid").toString()
                    val draftitemid = item.get("itemid").toString()
                    //Updated the uploaded picture.
                    attRepo.updatePictureMoodle(
                        context,
                        draftitemid,
                        userid,
                        onSuccess={result1->
                                onReceive(result1)
                            },
                        onError={result2->
                                onError(result2)
                            }
                        )
                }, onError={result->
                    onError(result)
                }
            )
    }
    fun getCourseListEnrolledByUser(userName:String,onReceiveData:(List<MoodleCourse>)->Unit,onError:(String)->Unit){
        attRepo.getUserCoursesListMoodle(context,userName, onError={result->
                onError(result)
            },onSuccess={result->
                val courseList = ArrayList<MoodleCourse>();
                for (i in 0 until result.length()){
                    courseList.add( MoodleCourse(
                        result.getJSONObject(i).getString("courseid"),
                        result.getJSONObject(i).getString("coursename"),
                        userName))
                }
                onReceiveData(courseList)
            }
        )
    }

    fun getStudentList(course:MoodleCourse,group:MoodleGroup,onReceiveData:(List<MoodleUserInfo>)->Unit,onError:(String)->Unit){
        attRepo.getEnrolledUserByCourseGroupMoodle(context,course.id,group.groupid,
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

    }


    fun getGroupInformation(course:MoodleCourse, onReceiveData: (List<MoodleGroup>) -> Unit, onError: (String) -> Unit){
        attRepo.getCourseGroupsMoodle(context,course.id,
            onError={result->
                onError(result)
            },onSuccess={result->
                val groupList=ArrayList<MoodleGroup>();
                for (i in 0 until result.length()){
                    groupList.add(MoodleGroup(course,
                        result.getJSONObject(i).getString("groupid"),
                        result.getJSONObject(i).getString("groupname")))
                }
                onReceiveData(groupList)

            }
        )
    }


    fun getSessionInformation(course:MoodleCourse,
                              onReceiveData: (List<MoodleSession>) -> Unit,
                              onError: (String) -> Unit){
        attRepo.getSessionsListMoodle(context,"57",onError={result->
                onError(result)
            },onSuccess={result->
                Log.i("MoodleRepository", "onSuccess: "+result.toString(4))
                val sessionList=ArrayList<MoodleSession>();
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
        )
    }


    fun getFacultyInformation(userName: String, onReceiveData: (List<MoodleUserInfo>) -> Unit, onError: (String) -> Unit){
        attRepo.getFacultyInfoMoodle(context,userName,onError={result->
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