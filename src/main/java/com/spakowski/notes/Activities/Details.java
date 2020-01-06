package com.spakowski.notes.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.spakowski.notes.Note.Note;
import com.spakowski.notes.R;

import tanlib.coders.RemainCoder;

public class Details extends AppCompatActivity {

    private int noteIndex;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        configureThis();
        configureUI();
    }

    private void configureThis(){
        String title = getIntent().getStringExtra("title");
        String detail = getIntent().getStringExtra("detail");
        this.note = new Note(title, detail);
        this.noteIndex = getIntent().getIntExtra("noteIndex", -1);
    }

    private void configureUI(){
        TextView showTitle = findViewById(R.id.showTitle);
        showTitle.setText(note.getTitle());
        TextView showDetails = findViewById(R.id.showDetails);
        showDetails.setText("");
        showDetails.setMovementMethod(new ScrollingMovementMethod());
        configureDecodeButton();
        configureDeleteButton();
    }

    private void configureDecodeButton(){
        Button decodeButton = findViewById(R.id.decodeButton);
        decodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decode();
            }
        });
    }

    private void decode(){
        EditText inputCode = findViewById(R.id.decodeNumber);
        String codeString = inputCode.getText().toString();
        String message;
        Integer code;
        if(codeString.length() > 0){
            code = Integer.parseInt(codeString);
            message = RemainCoder.decode(code, note.getMessage());
        }
        else{
            message = note.getMessage();
        }
        TextView showDetails = findViewById(R.id.showDetails);
        showDetails.setText(message);
        showDetails.setMovementMethod(new ScrollingMovementMethod());
    }

    private void configureDeleteButton(){
        Button deleteButton = findViewById(R.id.noteDeleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }
        });
    }

    private void confirm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Na pewno chcesz usunąć notatkę? Nie mozna tego wycofać.");
        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteNote();
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

    private void deleteNote(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("operation", "delete");
        returnIntent.putExtra("deleteIndex", noteIndex);
        setResult(Activity.RESULT_OK, returnIntent);
        Details.this.finish();
    }

}
