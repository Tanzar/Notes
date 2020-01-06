package com.spakowski.notes.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spakowski.notes.Adapters.NotesAdapter;
import com.spakowski.notes.File.FilesEditor;
import com.spakowski.notes.Note.Note;
import com.spakowski.notes.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Note> userNotes;
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        configureNotesList();
    }

    private void configureNotesList(){
        this.userNotes = getUserNotes();
        ListView list = findViewById(R.id.list);
        adapter = new NotesAdapter(this, userNotes);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listItemCLick(i);
            }
        });
    }

    private void listItemCLick(int index){
        Intent intent = new Intent(MainActivity.this, Details.class);
        Note note = userNotes.get(index);
        String title = note.getTitle();
        intent.putExtra("title", title);
        String detail = note.getMessage();
        intent.putExtra("detail", detail);
        intent.putExtra("noteIndex", index);
        startActivityForResult(intent, 1);
    }

    private List<Note> getUserNotes(){
        FilesEditor filesEditor = new FilesEditor(this);
        return filesEditor.getNotes();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1){
            String operation = data.getStringExtra("operation");
            if(operation.equals("delete")) {
                deleteNote(data);
            }
            if(operation.equals("add")){
                addNote(data);
            }
        }
    }

    private void addNote(Intent data) {
        String title = data.getStringExtra("title");
        String message = data.getStringExtra("message");
        Note newNote = new Note(title, message);
        this.userNotes.add(newNote);
        FilesEditor filesEditor = new FilesEditor(this);
        filesEditor.saveNote(newNote);
        adapter.notifyDataSetChanged();
    }

    public void deleteNote(Intent data){
        int index = data.getIntExtra("deleteIndex", -1);
        if (index != -1) {
            Note note = userNotes.get(index);
            FilesEditor filesEditor = new FilesEditor(this);
            filesEditor.deleteNote(note.getTitle());
            this.userNotes.remove(index);
            adapter.notifyDataSetChanged();
        }
    }

}
