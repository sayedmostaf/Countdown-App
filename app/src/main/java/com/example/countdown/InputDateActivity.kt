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
import java.util.Calendar

class InputDateActivity : ComponentActivity() {
    // Declare variables
    private lateinit var image: ImageView
    private lateinit var birthdayField: EditText
    private lateinit var nameField: TextView
    private var selectedImageUri: Uri? = null

    // make the photo clickable and pick photo from gallery
    var pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            image.setImageURI(uri)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // image action
        setContentView(R.layout.activity_input_data)
        image = findViewById(R.id.profile_image)
        image.setOnClickListener { pickImage.launch("image/*") }

        // take a date from dialog
        birthdayField = findViewById(R.id.birthday_field)
        birthdayField.setOnTouchListener { v, event ->
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

        // confirm button actions
        val calculateButton: Button = findViewById(R.id.confirm_button)
        nameField = findViewById(R.id.name_field)
        calculateButton.setOnClickListener {

            try {
                if (nameField.text.toString().trim().isEmpty()) throw Exception("Enter Your name")
                else if (birthdayField.text.toString().trim()
                        .isEmpty()
                ) throw Exception("Enter Your birthday")
                else {
                    // send data from fields
                    val name = nameField.text.toString()
                    val intent = Intent(this, CountDataActivity::class.java)
                    intent.putExtra("imageUri", selectedImageUri.toString())
                    intent.putExtra("name", name)
                    startActivity(intent)
                }
            } catch (ex: Exception) {
                Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Handle the selected date
                val selectedDate = "$year-${month + 1}-$dayOfMonth"
                birthdayField.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Set the maximum date to the current date
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis
        datePickerDialog.show()
    }

}