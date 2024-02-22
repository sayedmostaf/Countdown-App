package com.example.countdown

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class CountDataActivity : AppCompatActivity() {
    // Initializing UI elements
    private lateinit var nameView: TextView
    private lateinit var imageView: ImageView
    private lateinit var ageView: TextView
    private lateinit var timeLeftView: TextView
    private lateinit var daysView: TextView
    private lateinit var hoursView: TextView
    private lateinit var minutesView: TextView
    private lateinit var secondsView: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_data)

        // Retrieving data from intent
        val intent = intent
        val receivedName = intent.getStringExtra("name")
        val imageUriString = intent.getStringExtra("imageUri")
        val receivedAge = intent.getIntExtra("age", 0)
        val birthday = intent.getStringExtra("birthday")

        // Initializing UI elements by finding views by ID
        nameView = findViewById(R.id.name_text)
        imageView = findViewById(R.id.profile_image)
        ageView = findViewById(R.id.age_text)
        timeLeftView = findViewById(R.id.time_left_text)
        daysView = findViewById(R.id.days_text)
        hoursView = findViewById(R.id.hours_text)
        minutesView = findViewById(R.id.minutes_text)
        secondsView = findViewById(R.id.seconds_text)

        // Setting received data to UI elements
        nameView.text = receivedName
        ageView.text = receivedAge.toString()
        val newAge = receivedAge + 1
        timeLeftView.text = "Time Left To $newAge's Birthday"

        // Setting profile image if available
        val imageUri = imageUriString?.let { Uri.parse(it) }
        if (imageUri != null) {
            imageView.setImageURI(imageUri)
        }

        // Calculate time until next birthday and starting countdown timer
        var next = LocalDate.parse(
            birthday,
            DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        )
        next = next.plusYears(receivedAge.toLong())
        if (LocalDate.now() > next) next = next.plusYears(1)
        val millisUntilNextBirthday =
            Duration.between(LocalDateTime.now(), next.atStartOfDay()).toMillis()
        object : CountDownTimer(millisUntilNextBirthday, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var temp = millisUntilFinished
                // Updating countdown values on each tick
                // The remaining days
                daysView.text = (temp / (24 * 60 * 60 * 1000)).toString()
                if (daysView.text.length < 2) daysView.text = "0${daysView.text}"
                temp %= (24 * 60 * 60 * 1000)
                // The remaining hours
                hoursView.text = (temp / (60 * 60 * 1000)).toString()
                if (hoursView.text.length < 2) hoursView.text = "0${hoursView.text}"
                temp %= (60 * 60 * 1000)
                // The remaining minutes
                minutesView.text = (temp / (60 * 1000)).toString()
                if (minutesView.text.length < 2) minutesView.text = "0${minutesView.text}"
                temp %= (60 * 1000)
                // The remaining seconds
                secondsView.text = (temp / 1000).toString()
                if (secondsView.text.length < 2) secondsView.text = "0${secondsView.text}"
            }

            override fun onFinish() {
                // Displaying birthday message when countdown finishes
                timeLeftView.text = "Happy Birthday"
            }
        }.start() // Starting the countdown timer
    }
}
