package com.spakowski.notes.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.spakowski.notes.Note.Note;
import com.spakowski.notes.R;

import java.util.List;

public class NotesAdapter extends ArrayAdapter {

    private final Activity context;

    private List<Note> notes;

    public NotesAdapter(Activity context, List<Note> notes){
        super(context, R.layout.list_row, notes);
        this.context = context;
        this.notes = notes;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_row, null,true);
        TextView header = rowView.findViewById(R.id.titleView);
        header.setText(getTitle(position));
        return rowView;
    }
    private String getTitle(int position){
        Note note = this.notes.get(position);
        String title = note.getTitle();
        return title;
    }
}
