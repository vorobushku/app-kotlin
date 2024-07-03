package com.example.astrohelper

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.room.Room
import kotlin.random.Random
import android.widget.ImageView
import kotlinx.coroutines.withContext

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        val resultTextView: TextView = findViewById(R.id.resultText)
        GlobalScope.launch(Dispatchers.IO) {
            val database = Room.databaseBuilder(
                applicationContext,
                Database::class.java, "app_database"
            ).build()

            val magicBallDao = database.MagicBallDao()

            val randomId = Random.nextInt(1, 21) // Random number between 1 and 21
            val magicBall = magicBallDao.getMagicBallById(randomId)

            // Возвращаемся на основной поток для обновления пользовательского интерфейса
            withContext(Dispatchers.Main) {
                resultTextView.text = magicBall.text
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
}