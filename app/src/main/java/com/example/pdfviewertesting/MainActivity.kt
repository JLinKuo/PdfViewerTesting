package com.example.pdfviewertesting

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

internal const val PICK_PDF_RESULT = 555
internal const val TEMP_SAVE_FILES_PATH = "temp/"

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val selectPDf by lazy { findViewById<Button>(R.id.main_activity_select_pdf_file) }
    private val pdfViewer by lazy { findViewById<PDFView>(R.id.main_activity_pdfviewer) }

    private val dirPdfFolder by lazy {
        File(getExternalFilesDir(null), "$TEMP_SAVE_FILES_PATH")
    }
    private var pdfFileName = ""

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            PICK_PDF_RESULT -> {
                if(data != null) {
                    val dataUri = data.data
                    if(dataUri != null && Tools.isMimeTypeCorrect(this, dataUri, "PDF")) {
                        val cursor = contentResolver.query(dataUri,
                            null, null, null, null)
                        if(cursor != null && cursor.moveToFirst()) {
                            pdfFileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            val destPdfFile = Tools.getFullFile(dirPdfFolder, pdfFileName)
                            if(Tools.copyFileByUri(this, destPdfFile, dataUri, TAG)) {
                                openPDF(destPdfFile)
                            }
                            cursor.close()
                        }
                    } else {
                        showOneButtonNoMsgDialog(
                            getString(R.string.dialog_draft_pdf_select_type_error), null)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 開啟電子檔
        dirPdfFolder.listFiles()?.apply {
            openPDF(File(this[0].absolutePath))
        }

        selectPDf.setOnClickListener {
            Tools.selectAnPdf(this, "Select PDF File")
        }
    }

    private fun openPDF(pdfFile: File) {
        pdfViewer.fromFile(pdfFile)
                 .load()
    }

    private fun showOneButtonNoMsgDialog(title: String, listener: DialogInterface.OnClickListener?) {
        AlertDialog.Builder(this)
            .setCancelable(false).setTitle(title)
            .setPositiveButton(getString(R.string.dialog_confirm), listener)
            .show()
    }
}
