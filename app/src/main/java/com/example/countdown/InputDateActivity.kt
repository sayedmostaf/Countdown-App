package com.example.countdown

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class InputDateActivity : ComponentActivity() {
    // Declare variables
    private lateinit var image: ImageView
    private lateinit var birthdayField: EditText
    private lateinit var nameField: TextView
    private var selectedImageUri: Uri? = null

    // Activity result launcher for picking an image
    private var pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            image.setImageURI(uri)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data)

        // Find and set click listener for the profile image
        image = findViewById(R.id.profile_image)
        image.setOnClickListener { pickImage.launch("image/*") }
        // Find and set click listener for the birthday field to show date picker
        birthdayField = findViewById(R.id.birthday_field)
        birthdayField.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                // Calculate the position of the touch
                val drawableEnd = birthdayField.compoundDrawablesRelative[2]
                if (drawableEnd != null && event.rawX >= birthdayField.right - drawableEnd.bounds.width()) {
                    // Clicked on the drawable end
                    showDatePickerDialog()
                    return@setOnTouchListener true
                }
            }
            false
        }

        // Find and set click listener for the confirm button
        val calculateButton: Button = findViewById(R.id.confirm_button)
        nameField = findViewById(R.id.name_field)
        calculateButton.setOnClickListener {
            try {
                if (nameField.text.toString().trim().isEmpty()) throw Exception("Enter Your name")
                else if (birthdayField.text.toString().trim()
                        .isEmpty()
                ) throw Exception("Enter Your birthday")
                else {
                    val birthday = birthdayField.text.toString()
                    val age = calculateAge(birthday)

                    // Send data to CountDateActivity
                    val name = nameField.text.toString()
                    val intent = Intent(this, CountDataActivity::class.java)
                    intent.putExtra("imageUri", selectedImageUri.toString())
                    intent.putExtra("name", name)
                    intent.putExtra("age", age)
                    intent.putExtra("birthday", birthday)
                    startActivity(intent)
                }
            } catch (ex: Exception) {
                Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Calculate age based on birthday date
    private fun calculateAge(birthDate: String): Int {
        val birthday = LocalDate.parse(
            birthDate,
            DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        )
        val today = LocalDate.now()
        var age = today.year - birthday.year
        if (today.monthValue < birthday.monthValue ||
            (today.monthValue == birthday.monthValue && today.dayOfMonth < birthday.dayOfMonth)
        ) age--

        return age
    }

    // Show date picker dialog
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Handle the selected date
                val selectedDate = String.format("%03d-%02d-%02d", year, month + 1, dayOfMonth)
                birthdayField.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis
        datePickerDialog.show()
    }
}
