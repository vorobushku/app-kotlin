package com.example.astrohelper

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FifthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fifth)

        val database = Room.databaseBuilder(
            applicationContext,
            Database::class.java, "app_database"
        ).build()

        val dateOfBirth: String? = intent.getStringExtra("dateOfBirth")

        if (dateOfBirth != null) {
            val dateParts = dateOfBirth.split(".")
            if (dateParts.size == 3) {
                val day = dateParts[0].toInt()
                val month = dateParts[1].toInt()
                val year = dateParts[2].toInt()

                val modifiedDay = calculateModifiedDay(day)
                val talent = calculateModifiedDay(month)
                val finance = calculateModifiedDay(year)
                val sum3lasso = modifiedDay + talent + finance
                val karma = calculateModifiedDay(sum3lasso)
                val sum4lasso = karma + sum3lasso
                val comfort = calculateModifiedDay(sum4lasso)

                CoroutineScope(Dispatchers.Main).launch {
                    val numerologyDao = database.numerologyDao()
                    val numerologyIds = listOf(modifiedDay, talent, finance, karma, comfort)
                    val numerologyItems = numerologyIds.map { numerologyDao.getNumerologyById(it) }

                    val resultTextIds = listOf(
                        R.id.matrixresult_1_2 to R.id.matrixresult_1_1,
                        R.id.matrixresult_2_2 to R.id.matrixresult_2_1,
                        R.id.matrixresult_3_2 to R.id.matrixresult_3_1,
                        R.id.matrixresult_4_2 to R.id.matrixresult_4_1,
                        R.id.matrixresult_5_2 to R.id.matrixresult_5_1
                    )

                    numerologyItems.forEachIndexed { index, item ->
                        findViewById<TextView>(resultTextIds[index].first).text = item.text
                        findViewById<TextView>(resultTextIds[index].second).text = item.lasso.toString()
                    }
                }
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

    private fun calculateModifiedDay(day: Int): Int {
        return if (day <= 22) {
            day // Возвращаем день, если он меньше или равен 22
        } else {
            var currentDay = day
            while (currentDay > 22) {
                var sum = 0
                while (currentDay > 0) {
                    sum += currentDay % 10 // Получаем последнюю цифру числа и прибавляем к сумме
                    currentDay /= 10 // Уменьшаем число, отбрасывая последнюю цифру
                }
                currentDay = sum // Переходим к следующему циклу с новой суммой
            }
            return currentDay
        }
    }
}