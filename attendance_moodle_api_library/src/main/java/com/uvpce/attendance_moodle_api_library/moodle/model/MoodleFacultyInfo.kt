package com.example.guniattendancefaculty.moodle.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.example.guniattendancefaculty.moodle.Utility
import org.json.JSONArray

open class BaseUserInfo(var id: String, var username: String, var firstname: String, var lastname: String, var fullname:String, var imageUrl:String){
    var image:Bitmap = Utility().convertBase64StringToImage(Utility().convertUrlToBase64(imageUrl))
    override fun toString(): String {
        return "\n id=$id \n username=$username \n firstname=$firstname \n lastname=$lastname \n fullname=$fullname \n imageURL=$imageUrl"
    }

}


class MoodleUserInfo(var course: MoodleCourse,var group:MoodleGroup,id: String, username: String, firstname: String, lastname: String,
                        fullname:String, imageUrl:String):BaseUserInfo(id, username,firstname, lastname, fullname, imageUrl){


    override fun toString(): String {
        return super.toString()
    }
}