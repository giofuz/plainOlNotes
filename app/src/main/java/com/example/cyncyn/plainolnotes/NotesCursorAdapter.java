package com.example.cyncyn.plainolnotes;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Giovanni Fusciardi on 11/14/15.
 */
public class NotesCursorAdapter extends CursorAdapter {
    public NotesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(
                R.layout.note_list_item, parent, false
        );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String noteText = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.NOTE_TEXT));
        int pos = noteText.indexOf(10);
        if(pos != -1) {
            noteText = noteText.substring(0, pos) + " ...";
        }
        TextView tv = (TextView) view.findViewById(R.id.tvNote);
        tv.setText(noteText);


        String noteText2 = cursor.getString(
               cursor.getColumnIndex(DBOpenHelper.NOTE_EXPIRY));
        int pos2 = noteText2.indexOf(10);
        if(pos2 != -1){
            noteText2 = noteText.substring(0, pos) + " ...";
        }
        TextView tv2 = (TextView) view.findViewById(R.id.tvNote2);
        tv2.setText(noteText2);


        String noteText3 = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.NOTE_NUM));
        int pos3 = noteText3.indexOf(10);
        if(pos3 != -1){
            noteText3 = noteText.substring(0, pos) + " ...";
        }
        TextView tv3 = (TextView) view.findViewById(R.id.tvNote3);
        tv3.setText(noteText3);


    }
}
