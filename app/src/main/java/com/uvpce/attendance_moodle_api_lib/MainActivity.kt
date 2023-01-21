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
        val btn1= findViewById<Button>(R.id.btn1)
        val tv1 = findViewById<TextView>(R.id.tv1)
        tv1.setMovementMethod(ScrollingMovementMethod())
        btn1.setOnClickListener {
            val attRepo = MoodleController.getAttendanceRepository()
            val i = (Date().time / 1000).toInt()
            attRepo.createSessionMoodle(this, "2","Dhavanik_test2",i.toString(),"100000","9",7,object : ServerCallback {
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