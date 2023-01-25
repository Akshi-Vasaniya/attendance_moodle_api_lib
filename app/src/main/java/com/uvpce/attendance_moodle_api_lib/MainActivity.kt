package com.uvpce.attendance_moodle_api_lib

import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uvpce.attendance_moodle_api_library.MoodleController
import com.uvpce.attendance_moodle_api_library.QRMessageData
import com.uvpce.attendance_moodle_api_library.ServerCall
import com.uvpce.attendance_moodle_api_library.ServerCallback
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.Unit as Unit1


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn1 = findViewById<Button>(R.id.btn1)
        val tv1 = findViewById<TextView>(R.id.tv1)
        val iv1 = findViewById<ImageView>(R.id.iv1)
        tv1.setTextIsSelectable(true)
        tv1.movementMethod = ScrollingMovementMethod()
        val attRepo = MoodleController.
        getAttendanceRepository(
            "http://202.131.126.214",
            "4f3c9f8f0404a7db50825391c295937e",
            "697859a828111d63c3f68543ac986827",
        "8d29dd97dd7c93b0e3cdd43d4b797c87")
        QRMessageData(0,0,0,0,0,0,0,"null",0,"null", 0,0,0,0,0)
        btn1.setOnClickListener {
            val i = (Date().time / 1000).toInt()

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


        }
        tv1.setOnClickListener {
            val cm: ClipboardManager =this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.setText(tv1.getText())
            Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }

    }
    private fun encodeBitmapImage(bitmap: Bitmap):String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val bytesofimage = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytesofimage, Base64.DEFAULT)
    }
}