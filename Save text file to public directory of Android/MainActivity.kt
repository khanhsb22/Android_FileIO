package com.example.sampleapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sampleapp.databinding.ActivityMainBinding
import java.io.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nếu chưa cấp quyền truy cập WRITE_EXTERNAL_STORAGE thì xin yêu cầu cấp quyền
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1000)
        }

        binding.buttonSave.setOnClickListener {
            var fileName = binding.edtFilename.text.toString()
            var content = binding.edtContent.text.toString()
            saveFile(fileName, content)
        }

        binding.buttonView.setOnClickListener {
            readTextFile(binding.edtFilename.text.toString(), binding.edtView)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@MainActivity, "Permission granted !", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Permission not granted !", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    fun saveFile(fileName: String, content: String) {
        val _fileName = "$fileName.txt"

        // Create file
        //var path: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        var path: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS.toString() + "/sample_app")
        var file = File(path, _fileName)
        if (!path.exists()) {
            path.mkdir()
        }
        // Write to file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            fos.write(content.toByteArray())
            Toast.makeText(this@MainActivity, "Saved !", Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Toast.makeText(this@MainActivity, "File not found !", Toast.LENGTH_SHORT).show()
        } catch (e : IOException) {
            e.printStackTrace()
            Toast.makeText(this@MainActivity, "Error saving !", Toast.LENGTH_SHORT).show()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun readTextFile(fileName: String, editText: EditText) {
        val _fileName = "$fileName.txt"
        // Get file path
        var path: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS.toString() + "/sample_app")
        var file = File(path, _fileName)
        var fis: FileInputStream? = null
        // Read file
        try {
            fis = FileInputStream(file)
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            val sb = StringBuilder()
            var text: String?
            while (br.readLine().also { text = it } != null) {
                sb.append(text).append("\n")
            }
            /*Java code

            * String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            * */
            editText.text = Editable.Factory.getInstance().newEditable(sb.toString())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fis != null) {
                try {
                    fis.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}