package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.datebase.NotesDatabase
import com.example.myapplication.R

class NoteEdit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        //takes strings from intent so they can be edited.
        var oldTitle = intent.getStringExtra("title")
        var oldDescription = intent.getStringExtra("desc")
        var oldTimestamp = intent.getStringExtra("timestamp")


        //declarations
        val databaseHelper = NotesDatabase(this)
        val saveEditButton = findViewById<Button>(R.id.saveEdit)
        val cancelButton = findViewById<Button>(R.id.cancelEdit)
        var editTextTitle = findViewById<EditText>(R.id.editTextTitle)
        var editTextDescription = findViewById<EditText>(R.id.editTextDescription)


        //setting the edit texts to the values retrieved from intent passed the main activity passed
        editTextTitle.setText(oldTitle)
        editTextDescription.setText(oldDescription)

        //Calls database update query using old timestamp before swapping back to main activity.
        saveEditButton.setOnClickListener{

            //grabbing new variables from the current state of the edit texts when you click save
            var newTitle = editTextTitle.text.toString()
            var newDesc = editTextDescription.text.toString()

            //update note query with new variables
            databaseHelper.updateNote(newTitle, newDesc, oldTimestamp.toString())

            //intent to swap to main activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        //on click for cancel to swap back to main activity
        cancelButton.setOnClickListener{

            //intent to swap to main activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }
}