package com.example.astrohelper

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import kotlin.random.Random

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_third)

        GlobalScope.launch(Dispatchers.IO) {
            val database = Room.databaseBuilder(
                applicationContext,
                Database::class.java, "app_database"
            ).build()

            val tarotDao = database.tarotDao()
            val randomId = Random.nextInt(1, 79) // Random number between 1 and 78
            val tarotItem = tarotDao.getItemById(randomId)
            val imagePath = tarotItem.path
            Log.d("YourActivity", "Путь: $imagePath")

            // Обновление текста в TextView в основном потоке
            withContext(Dispatchers.Main) {
                findViewById<TextView>(R.id.resultText3).text = tarotItem.text
            }

            // Загрузка изображения и его установка в ImageView
            try {
                val inputStream: InputStream = assets.open(imagePath)
                val bitmap = BitmapFactory.decodeStream(inputStream)

                // Обновление изображения в ImageView в основном потоке
                withContext(Dispatchers.Main) {
                    findViewById<ImageView>(R.id.resultImage).setImageBitmap(bitmap)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Освобождение ресурсов связанных с изображением
        findViewById<ImageView>(R.id.resultImage).setImageBitmap(null)
    }
}