package com.example.alejandra_alvarez_20241212_herpm

import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.View

class MainActivity : AppCompatActivity() {

    private val READ_EXTERNAL_STORAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkStoragePermission()
    }

    // Solicitar permiso de almacenamiento
    private fun checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Para Android 11+ (API 30)
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:$packageName")
                startActivityForResult(intent, READ_EXTERNAL_STORAGE_REQUEST)
            } else {
                loadImagesFromGallery()
            }
        } else {
            // Para Android 6 - 10 (API 23 - 29)
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_EXTERNAL_STORAGE_REQUEST
                )
            } else {
                loadImagesFromGallery()
            }
        }
    }

    // Manejar la respuesta al permiso
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadImagesFromGallery()
        } else {
            Toast.makeText(this, "Permiso denegado para acceder al almacenamiento", Toast.LENGTH_SHORT).show()
        }
    }

    // Cargar imágenes desde la galería del dispositivo
    private fun loadImagesFromGallery() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val emptyMessage: TextView = findViewById(R.id.emptyMessage)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val imageList = mutableListOf<String>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media.DATA)

        contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            while (cursor.moveToNext()) {
                imageList.add(cursor.getString(columnIndex))
            }
        }

        if (imageList.isEmpty()) {
            // Mostrar el mensaje si no hay imágenes
            emptyMessage.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            // Mostrar las imágenes si están disponibles
            emptyMessage.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            val adapter = ImageAdapter(imageList) { imagePath ->
                Toast.makeText(this, "Imagen seleccionada: $imagePath", Toast.LENGTH_SHORT).show()
            }
            recyclerView.adapter = adapter
        }
    }
}
