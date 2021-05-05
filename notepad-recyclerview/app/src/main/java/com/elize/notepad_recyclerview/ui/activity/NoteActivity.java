package com.elize.notepad_recyclerview.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.elize.notepad_recyclerview.R;
import com.elize.notepad_recyclerview.model.Note;

import static com.elize.notepad_recyclerview.ui.activity.NoteActivityConstants.INVALID_POSITION;
import static com.elize.notepad_recyclerview.ui.activity.NoteActivityConstants.KEY_NOTE;
import static com.elize.notepad_recyclerview.ui.activity.NoteActivityConstants.KEY_POSITION;

public class NoteActivity extends AppCompatActivity {

    private static final String APPBAR_TITLE_ADD = "Add note";
    private static final String APPBAR_TITLE_UPDATE = "Update note";
    private int receivedPosition = INVALID_POSITION;
    private EditText editTextTitle;
    private EditText editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(APPBAR_TITLE_ADD);
        initializeFields();
        Intent data = getIntent();
        if (data.hasExtra(KEY_NOTE)) {
            setTitle(APPBAR_TITLE_UPDATE);
            Note receivedNote = data.getParcelableExtra(KEY_NOTE);
            receivedPosition = data.getIntExtra(KEY_POSITION, INVALID_POSITION);
            fillNoteFields(receivedNote);
        }
    }

    private void fillNoteFields(Note receivedNote) {
        editTextTitle.setText(receivedNote.getTitle());
        editTextDescription.setText(receivedNote.getDescription());
    }

    private void initializeFields() {
        editTextTitle = findViewById(R.id.note_title);
        editTextDescription = findViewById(R.id.note_description);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (isMenuNoteAdd(item)) {
            Note newNote = createNote();
            returnNoteResult(newNote);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnNoteResult(Note newNote) {
        Intent addResult = new Intent();
        addResult.putExtra(KEY_NOTE, newNote);
        addResult.putExtra(KEY_POSITION, receivedPosition);
        setResult(Activity.RESULT_OK, addResult);
    }

    private Note createNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        return new Note(title, description);
    }

    private boolean isMenuNoteAdd(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menu_note_add;
    }
}