package com.example.guniattendancefaculty.moodle.model

import android.graphics.Bitmap
import com.uvpce.attendance_moodle_api_library.model.ModelBase
import com.uvpce.attendance_moodle_api_library.model.MoodleCourse
import com.uvpce.attendance_moodle_api_library.model.MoodleGroup
import com.uvpce.attendance_moodle_api_library.util.Utility
import org.json.JSONObject

open class BaseUserInfo(var id: String,
                        var username: String,
                        var firstname: String,
                        var lastname: String,
                        var fullname:String,
                        var emailAddress:String,
                        var imageUrl:String): ModelBase
{
    var image:Bitmap = Utility().convertBase64StringToImage(Utility().convertUrlToBase64(imageUrl))
    companion object{
        fun fromJsonObject(jsonString: String): BaseUserInfo {
            val jsonObject = JSONObject(jsonString)

            return BaseUserInfo(
                jsonObject.getString("userId"),
                jsonObject.getString("userName"),
                jsonObject.getString("firstname"),
                jsonObject.getString("lastname"),
                jsonObject.getString("fullname"),
                jsonObject.getString("emailAddress"),
                jsonObject.getString("imageUrl")
            )
        }
    }
    override fun toJsonObject(): JSONObject {
        val json = JSONObject()
        json.put("userId",id)
        json.put("userName",username)
        json.put("firstname",firstname)
        json.put("lastname",lastname)
        json.put("fullname",fullname)
        json.put("emailAddress",emailAddress)
        json.put("imageUrl",imageUrl)
        return json
    }

    override fun toString(): String {
        return toJsonObject().toString(4)
    }

}


class MoodleUserInfo(var course: MoodleCourse, var group: MoodleGroup,
                     id: String, username: String,
                     firstname: String, lastname: String,
                     fullName:String, emailAddress:String, imageUrl:String):
    BaseUserInfo(id, username,firstname, lastname, fullName,emailAddress, imageUrl){


    override fun toString(): String {
        return super.toString() +"\n"+course.Name+"\n"+group.groupName
    }
}