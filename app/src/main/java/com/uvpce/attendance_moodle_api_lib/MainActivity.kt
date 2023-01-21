package com.uvpce.attendance_moodle_api_lib

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.uvpce.attendance_moodle_api_library.MoodleController
import com.uvpce.attendance_moodle_api_library.ServerCallback
import org.json.JSONArray
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn1 = findViewById<Button>(R.id.btn1)
        val tv1 = findViewById<TextView>(R.id.tv1)
        tv1.setMovementMethod(ScrollingMovementMethod())
        val attRepo = MoodleController.getAttendanceRepository("http://202.131.126.214/webservice/rest/server.php")
        btn1.setOnClickListener {
            attRepo.getMoodleUserCoursesList(this,"4f3c9f8f0404a7db50825391c295937e","admin",object:ServerCallback{
                override fun onSuccess(result: JSONArray) {
                    tv1.text=result.toString(4)
                }

                override fun onError(result: String) {
                    tv1.text=result
                }

            })

        }
    }
}