package com.uvpce.attendance_moodle_api_library.repo

import android.app.AlertDialog
import android.content.Context

class ClientAPI {
    companion object{
        const val attendanceToken = "697859a828111d63c3f68543ac986827"
        const val coreToken = "4f3c9f8f0404a7db50825391c295937e"
        const val fileUploadToken = "8d29dd97dd7c93b0e3cdd43d4b797c87"
        private const val userDefaultPicURL =
            "/webservice/pluginfile.php/89/user/icon/f1?token=4f3c9f8f0404a7db50825391c295937e"
        fun getDefaultUserPictureUrl(moodleUrl:String):String{
            return "$moodleUrl$userDefaultPicURL"
        }
        fun showErrorBox(context: Context, title: String, msg: String, negB:String = "", posB:String = "", cancelable: Boolean = true){
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle(title)
            alertDialogBuilder
                .setMessage(msg)
                .setCancelable(cancelable)
                .setNegativeButton(negB) { dialog, id ->
                    dialog.cancel()
                }
                .setPositiveButton(posB) { dialog, id ->
                    dialog.cancel()
                }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

}