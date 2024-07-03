package com.example.astrohelper

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.text.ParseException
import java.util.Locale

class FourthActivity : AppCompatActivity() {

    private var dateOfBirth: String = "" // Переменная для хранения даты рождения
    //@SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fourth)

        //проставление точек при вводе даты
        val editTextBirthday = findViewById<EditText>(R.id.birth_date)
        editTextBirthday.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))

        //val editTextBirthday = findViewById<EditText>(R.id.birth_date)
        editTextBirthday.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count == 1 && (start == 1 || start == 4)) { // если пользователь вводит символ второй или пятой позиции
                    editTextBirthday.append(".") // добавляем точку после второго и пятого символа
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Ничего не делаем перед изменением текста
            }
            override fun afterTextChanged(s: Editable?) {
                dateOfBirth = s.toString()
            }
        })

        /*val editText = findViewById<EditText>(R.id.birth_date)
        editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))*/

        findViewById<Button>(R.id.answermatrix).setOnClickListener {
            val input = editTextBirthday.text.toString()

            if (input.length == 10) {
                val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                sdf.isLenient = false // Делаем SimpleDateFormat строгим

                try {
                    // Пытаемся распарсить введенную дату
                    val date = sdf.parse(input)

                    // Проверяем, что дата не null и соответствует введенной дате
                    if (date != null && sdf.format(date) == input) {
                        // Дата введена корректно
                        val intent = Intent(this, FifthActivity::class.java)
                        intent.putExtra("dateOfBirth", input) // Передаем дату рождения в другую активность
                        startActivity(intent)
                    } else {
                        // Некорректная дата
                        runOnUiThread {
                            Toast.makeText(this, "Неверный формат даты", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: ParseException) {
                    // Ошибка при парсинге даты
                    runOnUiThread {
                        Toast.makeText(this, "Неверный формат даты", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                // Введена неполная дата
                runOnUiThread {
                    Toast.makeText(this, "Введите полную дату", Toast.LENGTH_SHORT).show()
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
}