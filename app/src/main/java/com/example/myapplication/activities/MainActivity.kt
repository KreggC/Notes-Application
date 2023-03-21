package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.NoteAdapter
import com.example.myapplication.datebase.NotesDatabase
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = NotesDatabase(this)
        val notes = dbHelper.queryDB()
        val adapter = NoteAdapter(notes = notes, this)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        val layoutManager = LinearLayoutManager(this)
        var button = findViewById<ImageButton>(R.id.newNoteButton)


        //assigning recycler view
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter


        //button onClickListener that passes intent to start note creation activity
        button.setOnClickListener {
            button.setBackgroundResource(R.drawable.ic_add_button_clicked)
            val intent = Intent(this, NoteCreate::class.java)
            startActivity(intent)


        }
    }
}