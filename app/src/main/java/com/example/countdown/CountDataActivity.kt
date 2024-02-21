package com.example.countdown

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView

class CountDataActivity : AppCompatActivity() {
    lateinit var namePreview: TextView
    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_data)
        val intent = getIntent()
        val receivedName = intent.getStringExtra("name")
        val imageUriString = intent.getStringExtra("imageUri")
        namePreview = findViewById(R.id.name_text)
        imageView = findViewById(R.id.profile_image)
        namePreview.text = receivedName
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            imageView.setImageURI(imageUri)
        }
    }
}