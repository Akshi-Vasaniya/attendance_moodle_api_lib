package com.uvpce.attendance_moodle_api_lib

import android.graphics.Bitmap
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.uvpce.attendance_moodle_api_library.model.QRMessageData
import com.uvpce.attendance_moodle_api_library.util.Utility
import java.io.ByteArrayOutputStream
import java.util.*


class MainActivity : AppCompatActivity() {
    val modelRepo = MoodleConfig.getModelRepo(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn1 = findViewById<Button>(R.id.btn1)
        val tv1 = findViewById<TextView>(R.id.tv1)
        val iv1 = findViewById<ImageView>(R.id.iv1)
        tv1.setTextIsSelectable(true)
        tv1.movementMethod = ScrollingMovementMethod()



        btn1.setOnClickListener {
            val i = (Date().time / 1000).toInt()
            var string = ""
            modelRepo.getCourseListEnrolledByUser("admin",{ courseList->
                    //string += "\nCourses:"+courseList.joinToString("\n")
                    val selectedCourse = courseList[5]
                    modelRepo.getGroupList(selectedCourse, onReceiveData = {
                    //string += "Groups:"+groupList.joinToString("\n")
                        Log.i("MainActivity", "onCreate: Selected Course:$selectedCourse")
                        string +="\n\nSelected Course = $selectedCourse\n"
                        val selectedGroup = selectedCourse.groupList[5]
                        Log.i("MainActivity", "onCreate: Selected Course:$selectedGroup")
                        string +="\n\nSelected Group = $selectedCourse\n"
                        //Test Login
                        modelRepo.getStudentInfo("hms",
                            onReceive = {user->
                                            string += user.toString()
                                            modelRepo.getAttendance(courseList[5], onReceiveData = {attendance->
                                            string += "\nAttendance:\n$attendance\n"
                                            Log.i("MainActivity", "onCreate: ${attendance.toString()}")
                                            /*modelRepo.createSession(selectedGroup,attendance,
                                                created_by_user_id=user.id,
                                            sessionStartTimeInSeconds = Utility().getSeconds(11,0)
                                            , sessionDuration = Utility().getDurationInSeconds(0,50),
                                                description = "Session taken by HMS",
                                                onError = {resultError->
                                                    Log.i("MainActivity", "createSession: Error:$resultError")
                                                },
                                                onReceiveData = {
                                                    session->
                                                    Log.i("MainActivity", "createSession: Session created Successfully:\n$session")
                                                    string += "\nSession:\n$session\n"

                                                }
                                            )*/
                                        modelRepo.getSessionList(selectedCourse,attendance,selectedGroup, needUserList = true, onError = {}, onReceiveData = { sessionList->

                                            string += "\n"+sessionList.joinToString("\n")
                                            val selectedSession = sessionList[0]
                                            string += "\n Attendance: Session Count:${selectedSession.getAttendanceCount().toString()}"
                                            val selectedSessionUser =selectedSession.userList[15]
                                            modelRepo.getQRDataString(
                                                QRMessageData(
                                                    session = selectedSession,user.id,user.id,
                                                    "xyzlat","xyzlong",
                                                    Utility().getSeconds(10,50),Utility().getSeconds(11,0),
                                                    Utility().getDurationInSeconds(0,10)
                                                ),
                                                onSuccess = {
                                                        qrString->
                                                    string+= "\n\nQR String:$qrString\n\n"
                                                    val student_id = selectedSessionUser.id
                                                    modelRepo.takePresentAttendance(qrString,student_id, onSuccess = {
                                                            jsonResult->
                                                        string+="\nFor User:$selectedSessionUser ${jsonResult}\n"
                                                        //string += "\n After Attendance: Session Count:${selectedSession.getAttendanceCount().toString()}"
                                                        tv1.text = string
                                                    }, onError = {error->
                                                        Log.i(
                                                            "MainActivity",
                                                            "onCreate: takePresentAttendance:$error"
                                                        )
                                                        tv1.text =
                                                            "$string\nError:: takePresentAttendance:$error"
                                                    }
                                                    )

                                                },
                                                onError = {}
                                            )

                                        })


                                    }, onError = {resultError->

                                            Log.e("MainActivity", "onCreate: $resultError")
                                    })
                                /*MoodleAttendanceBulk(modelRepo,courseList).
                                createAttendanceInBulk(onSuccess = {attendanceList->
                                    var log = attendanceList.joinToString("\n")
                                    Log.i("MainActivity:Sample", "onCreate,Attendance:\n $log")
                                    string += log
                                })*/


                            }, onError = {
                                string += it
                                tv1.text = string
                            })

                }, onError = {

                })
            }, onError = {

            })

//            val attRepo = AttendanceRepository(this,
//                "http://202.131.126.214",
//                "4f3c9f8f0404a7db50825391c295937e",
//                "697859a828111d63c3f68543ac986827",
//                "8d29dd97dd7c93b0e3cdd43d4b797c87")
//            attRepo.sendMessageMoodle(this,"5","null","null","null","null","null","null","null","null",object:ServerCallback{
//                override fun onSuccess(result: JSONArray) {
//                    tv1.text=result.toString(4)
//                }
//
//                override fun onError(result: String) {
//                    tv1.text=result
//                }
//
//            })
//            attRepo.getFacultyInfoMoodle(this,"admin", object:ServerCallback{
//                override fun onSuccess(result: JSONArray) {
//                    tv1.text=result.toString(4)
//                }
//
//                override fun onError(result: String) {
//                    tv1.text=result
//                }
//
//            })
//            attRepo.createAttendanceMoodle(this,"34","dk6514",object : ServerCallback{
//                override fun onSuccess(result: JSONArray) {
//                    tv1.text=result.toString(4)
//                }
//
//                override fun onError(result: String) {
//                    tv1.text=result
//                }
//            })
//            attRepo.getUserInfoMoodle(this,"vrp",object:ServerCallback{
//                override fun onSuccess(result: JSONArray) {
//                    tv1.text=result.toString(4)
//                }
//
//                override fun onError(result: String) {
//                    tv1.text=result
//                }
//            })
//            attRepo.createSessionMoodle(this,"34","56", i.toString(),"100000","30",object:ServerCallback{
//                override fun onSuccess(result: JSONArray) {
//                    tv1.text=result.toString(4)
//                }
//
//                override fun onError(result: String) {
//                    tv1.text=result
//                }
//
//            })
//            attRepo.getSessionsListMoodle(this,"52", object:ServerCallback{
//                override fun onSuccess(result: JSONArray) {
//                    tv1.text=result.toString(4)
//                }
//
//                override fun onError(result: String) {
//                    tv1.text=result
//                }
//
//            })
//            attRepo.getCourseGroupsMoodle(this,"35",object:ServerCallback{
//                override fun onSuccess(result: JSONArray) {
//                    tv1.text=result.toString(4)
//                }
//
//                override fun onError(result: String) {
//                    tv1.text=result
//                }
//
//            })
//            attRepo.getUserCoursesListMoodle(this,"admin",object :ServerCallback{
//                override fun onError(result: String) {
//                    tv1.text=result
//                }
//
//                override fun onSuccess(result: JSONArray) {
//                    tv1.text=result.toString(4)
//                }
//            })
//            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.gnu_logo)
//            var file_content = encodeBitmapImage(bitmap)
//            attRepo.uploadFileMoodle(this,"user","draft","0","/","moodleProfile.jpg",file_content,"user","2",object:
//                ServerCallback {
//                override fun onSuccess(result: JSONArray) {
//                    tv1.text=result.toString(4)
//                }
//
//                override fun onError(result: String) {
//                    tv1.text=result
//                }
//            })

//            attRepo.updatePictureMoodle(this,"463630892","2",object :ServerCallback{
//                override fun onSuccess(result: JSONArray) {
//                    tv1.text=result.toString(4)
//                }
//
//                override fun onError(result: String) {
//                    tv1.text=result
//                }
//
//            })

//            attRepo.getMessageMoodle(this,"5","conversations","2",object: ServerCall {
//                override fun onSuccess(result: JSONObject) {
//                    tv1.text=result.toString(4)
//                }
//
//                override fun onError(result: String) {
//                    tv1.text=result
//                }
//
//            })
//            attRepo.getCategoriesMoodle(this,object :ServerCallback{
//                override fun onSuccess(result: JSONArray) {
//                    tv1.text=result.toString(4)
//                }
//
//                override fun onError(result: String) {
//                    tv1.text=result
//                }
//            })
//            attRepo.takeAttendanceMoodle(this,"434","2","5","22","21",object:ServerCallback{
//                override fun onSuccess(result: JSONArray) {
//                    tv1.text=result.toString(4)
//                }
//
//                override fun onError(result: String) {
//                    tv1.text=result
//                }
//            })
//            attRepo.getCohorts(this,object:ServerCallback{
//                override fun onSuccess(result: JSONArray) {
//                    tv1.text=result.toString(4)
//                }
//
//                override fun onError(result: String) {
//                    tv1.text=result
//                }
//            })
//            val arrayList = ArrayList<String>()
//            arrayList.add("5")
//            arrayList.add("2")
//            attRepo.getUserByFieldMoodle(this,"id",arrayList,object :ServerCallback{
//                override fun onSuccess(result: JSONArray) {
//                    tv1.text=result.toString(4)
//                }
//
//                override fun onError(result: String) {
//                    tv1.text=result
//                }
//            })
//            attRepo.getCohortMembersMoodle(this,26,object :ServerCallback{
//                override fun onSuccess(result: JSONArray) {
//                    tv1.text=result.toString(4)
//                }
//
//                override fun onError(result: String) {
//                    tv1.text=result
//                }
//            })
//            var url="http://202.131.126.214/pluginfile.php/3370/user/icon/classic/f1?rev=21913"
//            var token = "8d29dd97dd7c93b0e3cdd43d4b797c87"
//            tv1.text = attRepo.resolveImgURLMoodle(url,token)

        }
        tv1.setOnClickListener {
            /*val cm: ClipboardManager =this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.setText(tv1.getText())
            Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show()*/
        }

    }
    private fun encodeBitmapImage(bitmap: Bitmap):String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val bytesofimage = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytesofimage, Base64.DEFAULT)
    }

}