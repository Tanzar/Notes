package com.spakowski.notes.Activities;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.spakowski.notes.File.FilesEditor;
import com.spakowski.notes.Note.Note;
import com.spakowski.notes.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import tanlib.coders.RemainCoder;

public class NewNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Button addButton = findViewById(R.id.newNoteButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }
        });
    }

    private void confirm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Zapisać notatkę?");
        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveNote();
            }
        });
        builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    private void saveNote(){
        EditText titleInput = findViewById(R.id.newTitle);
        TextInputEditText messageInput = findViewById(R.id.newMessage);
        String title = titleInput.getText().toString();
        if(checkTitle(title)) {
            String message = messageInput.getText().toString();
            EditText encodeInput = findViewById(R.id.newEncodeNumber);
            String encodeNumber = encodeInput.getText().toString();
            if (encodeNumber.length() > 0) {
                Integer code = Integer.parseInt(encodeNumber);
                message = RemainCoder.encode(code, message);
            }
            addNote(title, message);
        }
    }

    private boolean checkTitle(String title){
        if(title.trim().isEmpty()){
            alert("Notatka nie może być pusta.");
            return false;
        }
        else {
            FilesEditor filesEditor = new FilesEditor(this);
            if (filesEditor.titleTaken(title)) {
                alert("Tytuł notatki nie może się powtarzać.");
                return false;
            }
        }
        return true;
    }

    private void alert(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    private void addNote(String title, String message){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("operation", "add");
        returnIntent.putExtra("title", title);
        returnIntent.putExtra("message", message);
        setResult(Activity.RESULT_OK, returnIntent);
        NewNoteActivity.this.finish();
    }
}
