package com.example.myapplication.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.datebase.NotesDatabase
import com.example.myapplication.R
import com.example.myapplication.activities.MainActivity
import com.example.myapplication.activities.NoteEdit
import com.example.myapplication.model.Note
import java.text.DateFormat

class NoteAdapter(private val notes : List<Note>, internal var context: Context) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {





    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.dbTitleOutput)
        val desc : TextView = itemView.findViewById(R.id.dbDescOutput)
        val timestamp : TextView = itemView.findViewById(R.id.dbTimeOutput)
        val edit_button : ImageButton = itemView.findViewById(R.id.recyclerEdit)
        val del_button : ImageButton = itemView.findViewById(R.id.recyclerDelete)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout, parent, false)
        val viewHolder = ViewHolder(view)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //binding notes to viewholder based on position in arraylist and creating onClickListeners for edit and delete buttons
        val note = notes[position]

        holder.title.text = note.title
        holder.desc.text = note.description
        val formattedTime : String = DateFormat.getDateTimeInstance().format(note.timestamp.toLong())
        holder.timestamp.text = formattedTime


        //delete button onClickListener
        holder.del_button.setOnClickListener {

            //initializing DBHelper
            val dbHelper = NotesDatabase(this.context)


            //dialog to verify if user wants to delete the note
            val dialog = AlertDialog.Builder(this.context).setTitle("Delete this note?").setMessage("Would you like to delete this note?")
                .setPositiveButton("Yes") { dialog, _ ->
                    dbHelper.deleteNote(note.timestamp)
                    dialog.dismiss()


                    //refresh list temporarily just restart activity
                    val intent = Intent(this.context, MainActivity::class.java)
                    context.startActivity(intent)

                }

                //no option of dialog
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }

            //show dialog
            dialog.show()



        }

        //edit button onClickListener
        holder.edit_button.setOnClickListener{

            //sending the Note variables as an intent for the NoteEdit class
            val intent = Intent(this.context, NoteEdit::class.java)
            intent.putExtra("title", note.title)
            intent.putExtra("desc", note.description)
            intent.putExtra("timestamp", note.timestamp)
            context.startActivity(intent)
        }
    }


    override fun getItemCount() = notes.size

}

