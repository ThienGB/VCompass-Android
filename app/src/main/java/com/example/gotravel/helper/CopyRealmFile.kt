package com.example.gotravel.helper

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

object CopyRealmFile {
    fun copyRealmFile(context: Context) {
        // Tên file Realm
        val realmFileName = "qlphongban1.realm"
        // Đường dẫn file Realm
        val realmFile = File(context.filesDir, realmFileName)
        // Đường dẫn đích để sao chép file Realm
        val downloadsDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val destinationFile = File(downloadsDirectory, realmFileName)
        if (realmFile.exists()) {
            try {
                copyFile(realmFile, destinationFile)
                Log.d("Realm", "Realm file copied to: " + destinationFile.absolutePath)
            } catch (e: IOException) {
                Log.e("Realm", "Error copying Realm file", e)
            }
        } else {
            Log.e("Realm", "Realm file not found")
        }
    }

    @Throws(IOException::class)
    private fun copyFile(sourceFile: File, destinationFile: File) {
        val fis = FileInputStream(sourceFile)
        val fos = FileOutputStream(destinationFile)

        val buffer = ByteArray(1024)
        var length: Int
        while ((fis.read(buffer).also { length = it }) > 0) {
            fos.write(buffer, 0, length)
        }

        fis.close()
        fos.close()
    }
}
