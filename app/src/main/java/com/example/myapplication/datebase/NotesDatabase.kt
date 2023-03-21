package com.example.myapplication.datebase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import com.example.myapplication.model.Note


class NotesDatabase(context: Context) : SQLiteOpenHelper(context, DB_Name, null, 1) {

    //companion object to hold values for easier usage
    companion object {
        private const val DB_Name = "NotesDB"
        private const val Table = "Notes"
        private const val ID = "id"
        private const val Title = "title"
        private const val Desc = "Description"
        private const val Timestamp = "timestamp"
    }


    //create database
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableSQL = "CREATE TABLE $Table ($ID INTEGER PRIMARY KEY, $Title TEXT, $Desc TEXT, $Timestamp TEXT);"
        db?.execSQL(createTableSQL)
    }

    //upgrading database drops original auto generated method
    override fun onUpgrade(db: SQLiteDatabase?, p0: Int, p1: Int) {
        val updateSQL = "DROP TABLE IF EXISTS $Table"
        db?.execSQL(updateSQL)
        onCreate(db)
    }


    //Queries database and returns results as Note objects in a List
    fun queryDB() : List<Note> {

        //declarations
        val notes = ArrayList<Note>()
        val db = this.writableDatabase
        val selectSQL = "SELECT * FROM $Table"
        val cursor = db.rawQuery(selectSQL, null)

        if( cursor != null ) {
            if (cursor.moveToFirst()) {
                do {

                    //create note through cursor parsing the database
                    val note = Note()

                    //Check values are for dealing with potential errors for a column not existing since getColumnIndex can return a value that isnt -1
                    val idCheck = cursor.getColumnIndex(ID)
                    if (idCheck == -1) continue
                    note.id = Integer.parseInt(cursor.getString(idCheck))

                    val titleCheck = cursor.getColumnIndex(Title)
                    if (titleCheck == -1) continue
                    note.title = cursor.getString(titleCheck)

                    val descCheck = cursor.getColumnIndex(Desc)
                    if (descCheck == -1) continue
                    note.description = cursor.getString(descCheck)

                    val timestampCheck = cursor.getColumnIndex(Timestamp)
                    if (timestampCheck == -1) continue
                    note.timestamp = cursor.getString(timestampCheck)

                    //add the created note to the list
                    notes.add(note)
                } while (cursor.moveToNext())
            }
        }
        db.close()

        //returns the arraylist for recycler view
        return notes
    }

    //function to add a note to the database, takes in a title, description, and timestamp.
    fun addNote(title : String, description : String, timestamp : String) {

        val db = this.writableDatabase
        val contentValues = ContentValues()

        //putting fields in contentValues for insertion
        contentValues.put(Title, title)
        contentValues.put(Desc, description)
        contentValues.put(Timestamp, timestamp)

        //nullColumnHack for autoincrement ID
        db.insert(Table, null, contentValues)
        db.close()

    }

    //function to update note within the database, uses timestamp.
    fun updateNote(title: String, description: String, timestamp: String){

        val db = this.writableDatabase
        val newTimestamp = System.currentTimeMillis().toString()
        val contentValues = ContentValues()

        //putting values into contentValues for updating the database
        contentValues.put(Title, title)
        contentValues.put(Desc, description)
        contentValues.put(Timestamp, newTimestamp)
        db.update(Table, contentValues, "$Timestamp=?", arrayOf(timestamp))
        db.close()

    }

    //function to delete note within the database, uses timestamp.
    fun deleteNote(timestamp: String){

        val db = this.writableDatabase
        db.delete(Table, "$Timestamp=?", arrayOf(timestamp))
        db.close()

    }

}

