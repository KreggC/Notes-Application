package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.datebase.NotesDatabase
import com.example.myapplication.R

class NoteCreate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_create)

        //instantiating database when class is loaded
        var database = NotesDatabase(this)

        //declaring variables and binding them to elements
        var cancelButton = findViewById<Button>(R.id.cancelCreate)
        var addButton = findViewById<Button>(R.id.createNote)
        var titleEdit = findViewById<EditText>(R.id.editTextTitle)
        var descEdit = findViewById<EditText>(R.id.editTextDescription)

        //onClickListener for cancel button that sends you back to main activity
        cancelButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        //onClickListener for add button, calls insert method from database helper to insert the values entered to the database.
        addButton.setOnClickListener {

            var Title = titleEdit.text.toString()
            var Desc = descEdit.text.toString()
            var Timestamp = System.currentTimeMillis().toString()


            database.addNote(Title, Desc, Timestamp)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}