package com.uvpce.attendance_moodle_api_library.model

import com.example.guniattendancefaculty.moodle.model.MoodleCourse

class MoodleAttendance(val course:MoodleCourse,val attendanceId:String, val attendanceName:String) {
}