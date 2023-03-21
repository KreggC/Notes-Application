package com.example.myapplication

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.datebase.NotesDatabase
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class NotesDatabaseTest {

   lateinit var db : NotesDatabase

    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = NotesDatabase(context)
    }



    @org.junit.Test
    fun addNoteTest() {

        //values that will be inserted
        val title = "Title"
        val description = "Description"
        val timestamp = System.currentTimeMillis().toString()

        //inserting the values
        db.addNote(title, description, timestamp)

        //querying db to test if insertion was successful
        val notes = db.queryDB()

        //checking if things were inserted correctly
        assertEquals(1, notes.size)
        assertEquals(title, notes[0].title)
        assertEquals(description, notes[0].description)
        assertEquals(timestamp, notes[0].timestamp)

        db.close()

    }

    @org.junit.Test
    fun updateNoteTest(){

        //values that will be inserted
        val title = "Title"
        val description = "Description"
        val timestamp = System.currentTimeMillis().toString()

        //value that will be updated
        val newTitle = "newTitle"

        //insert note
        db.addNote(title, description, timestamp)

        //update note
        db.updateNote(newTitle, description, timestamp)

        //querying db to test if update was successful
        val notes = db.queryDB()

        //asserting updated values are correct
        assertEquals(1, notes.size)
        assertEquals(newTitle, notes[0].title)
        assertNotEquals(timestamp, notes[0].timestamp)

        db.close()

    }

    @org.junit.Test
    fun deleteNoteTest(){
        //values that will be inserted
        val title = "Title"
        val description = "Description"
        val timestamp = System.currentTimeMillis().toString()

        //insert note
        db.addNote(title, description, timestamp)

        //delete note
        db.deleteNote(timestamp)

        //querying db to test if delete worked
        val notes = db.queryDB()

        //checking values
        assertEquals(0, notes.size)

        db.close()
    }
}



