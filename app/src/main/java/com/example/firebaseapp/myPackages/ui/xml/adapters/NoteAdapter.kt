package com.example.firebaseapp.myPackages.ui.xml.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.firebaseapp.R
import com.example.firebaseapp.myPackages.data.models.NoteContent

class NoteAdapter(
    private val context: Context,
    private val notes: List<NoteContent>,
    private val onDeleteClick:(NoteContent)-> Unit= {},
    private val onUpdateClick:(NoteContent)-> Unit = {},
    private val onItemClick:(NoteContent)-> Unit = {}
    ) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
            as LayoutInflater

    override fun getCount(): Int {
       return notes.size
    }

    override fun getItem(p0: Int): Any {
      return  notes[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
         val rowView = LayoutInflater.from(context).inflate(R.layout.note_model,p2,false)
         val item: NoteContent = getItem(p0) as NoteContent
         val title = rowView.findViewById<TextView>(R.id.title)
         val date = rowView.findViewById<TextView>(R.id.date)
         val deleteBtn = rowView.findViewById<ImageView>(R.id.delete_icon)
         val editBtn = rowView.findViewById<ImageView>(R.id.edit_icon)

         title.text = item.title
         date.text = item.date

        deleteBtn.setOnClickListener {
            onDeleteClick(item)
        }

        editBtn.setOnClickListener {
           onUpdateClick(item)
        }

        rowView.setOnClickListener {
            onItemClick(item)
        }

        return rowView
    }

}