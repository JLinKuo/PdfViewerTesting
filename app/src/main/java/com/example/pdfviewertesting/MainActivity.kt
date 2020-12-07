package com.example.pdfviewertesting

import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.scale
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

internal const val PICK_PDF_RESULT = 555
internal const val TEMP_SAVE_FILES_PATH = "temp/"

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val mainActivity by lazy { findViewById<ConstraintLayout>(R.id.main_activity_layout) }
    private val selectPDf by lazy { findViewById<Button>(R.id.main_activity_select_pdf_file) }
    private val addASignArea by lazy { findViewById<Button>(R.id.main_activity_add_a_sign_name_area) }
    private val pdfViewer by lazy { findViewById<PDFView>(R.id.main_activity_pdfviewer) }

    private val dirPdfFolder by lazy {
        File(getExternalFilesDir(null), "$TEMP_SAVE_FILES_PATH")
    }
    private var pdfFileName = ""

    private val minSignAreaWidth = 180
    private val minSignAreaHeight = minSignAreaWidth / 2

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

        addASignArea.setOnClickListener {
            addAnSignNameArea()
        }
    }

    private fun addAnSignNameArea() {

    }

    private fun addAnWatermark(canvas: Canvas, pageWidth: Float, pageHeight: Float, zoom: Float) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_round)
        val scaleBitmap = bitmap.scale((bitmap.width * zoom).toInt(), (bitmap.height * zoom).toInt())
        canvas.drawBitmap(scaleBitmap, (pageWidth - scaleBitmap.width) / 2, (pageHeight - scaleBitmap.height) / 2, Paint());
        bitmap.recycle()
        scaleBitmap.recycle()
        pdfViewer.invalidate()
    }

    private fun openPDF(pdfFile: File) {
        pdfViewer.fromFile(pdfFile)
                 .onDraw { canvas, pageWidth, pageHeight, zoom, displayedPage ->
                     addAnWatermark(canvas, pageWidth, pageHeight, zoom)
                 }
                 .load()
    }

    private fun showOneButtonNoMsgDialog(title: String, listener: DialogInterface.OnClickListener?) {
        AlertDialog.Builder(this)
            .setCancelable(false).setTitle(title)
            .setPositiveButton(getString(R.string.dialog_confirm), listener)
            .show()
    }
}
