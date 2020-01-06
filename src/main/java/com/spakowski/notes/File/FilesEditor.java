package com.spakowski.notes.File;

import android.content.Context;

import com.spakowski.notes.Note.Note;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FilesEditor {

    private Context context;

    public FilesEditor(Context context){
        this.context = context;
    }

    public List<Note> getNotes(){
        List<String> titles = getNotesTitles();
        List<Note> notes = new ArrayList<Note>();
        for (String title: titles){
            Note note = readNoteFile(title);
            notes.add(note);
        }
        return notes;
    }

    public void saveNote(Note note){
        String title = note.getTitle();
        List<String> message = new ArrayList<String>();
        message.add(note.getMessage());
        writeToFile(title, message);
        addTitleToList(title);
    }

    public void deleteNote(String title){
        File file = new File(context.getFilesDir(), title);
        file.delete();
        List<String> titles = getNotesTitles();
        int index = titles.indexOf(title);
        titles.remove(index);
        writeToFile("noteTitles", titles);
    }

    public boolean titleTaken(String title){
        List<String> titlesTaken = getNotesTitles();
        return titlesTaken.contains(title);
    }

    private void addTitleToList(String title){
        List<String> titles = getNotesTitles();
        titles.add(title);
        writeToFile("noteTitles", titles);
    }

    public List<String> getNotesTitles(){
        return readFile("noteTitles");
    }

    public Note readNoteFile(String filename){
        Note note = new Note();
        note.setTitle(filename);
        List<String> textLines = readFile(filename);
        String message = "";
        for (String line: textLines){
            message += line;
        }
        note.setMessage(message);
        return note;
    }

    private void writeToFile(String filename, List<String> textLines){
        OutputStreamWriter outputStream;
        File file = new File(context.getFilesDir(), filename);
        try {
            outputStream = new OutputStreamWriter(context.openFileOutput(file.getName(), Context.MODE_PRIVATE));
            for(String line: textLines){
                outputStream.append(line + "\n");
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> readFile(String filename){
        List<String> result = new ArrayList<String>();
        try {
            return read(filename);
        }
        catch (FileNotFoundException e) {
        }
        catch (IOException e) {
        }
        return result;
    }

    private List<String> read(String filename) throws IOException {
        InputStream inputStream = context.openFileInput(filename);
        List<String> data = new ArrayList<String>();
        if ( inputStream != null ) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            while ( (receiveString = bufferedReader.readLine()) != null ) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(receiveString);
                data.add(stringBuilder.toString());
            }
            inputStream.close();
            return data;
        }
        else {
            return data;
        }
    }
}
