package com.example.pdfviewertesting

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.DisplayMetrics
import android.webkit.MimeTypeMap
import java.io.*

class Tools {
    companion object {
        fun getScreenMetric(activity: Activity): DisplayMetrics {
            val metric = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(metric)
            return metric
        }

        fun selectAnPdf(activity: Activity, title: String) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "pdf/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.putExtra(Intent.EXTRA_MIME_TYPES, "application/pdf")
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            activity.startActivityForResult(Intent.createChooser(intent, title), PICK_PDF_RESULT)
        }

        fun isMimeTypeCorrect(context: Context, uri: Uri, type: String): Boolean {
            val extension = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
                val mime = MimeTypeMap.getSingleton()
                mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
            } else {
                MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
            }
            return extension.equals(type, ignoreCase = true)
        }

        fun getFullFile(file: File, fileName: String): File {
            if (!file.exists()) {
                file.mkdirs()
            }

            return File(file.absolutePath, fileName)
        }

        fun copyFileByUri(context: Context, destFile: File, uri: Uri, className: String): Boolean {
            var result = false
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                inputStream = context.contentResolver.openInputStream(uri)
                outputStream = FileOutputStream(destFile)
                val buf = ByteArray(1024)
                var len: Int
                while (inputStream!!.read(buf).also { len = it } > 0) {
                    outputStream.write(buf, 0, len)
                }
                result = true
            } catch(ex: IOException) {
                result = false
            } catch(ex: Exception) {
                result = false
            } finally {
                outputStream?.close()
                inputStream?.close()
            }

            return result
        }
        fun dp2Pixel(context: Context, dp: Int): Int {
            return (dp * getDensity(context)).toInt()
        }

        fun dp2Pixel(context: Context, dp: Float): Int {
            return (dp * getDensity(context)).toInt()
        }
        private fun getDensity(context: Context): Float {
            return context.resources.displayMetrics.density
        }
    }
}