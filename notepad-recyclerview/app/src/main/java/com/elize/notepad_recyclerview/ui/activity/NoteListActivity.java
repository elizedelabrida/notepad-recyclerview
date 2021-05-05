package com.elize.notepad_recyclerview.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.elize.notepad_recyclerview.R;
import com.elize.notepad_recyclerview.dao.NoteDAO;
import com.elize.notepad_recyclerview.model.Note;
import com.elize.notepad_recyclerview.ui.recycler.adapter.NoteListAdapter;
import com.elize.notepad_recyclerview.ui.recycler.helper.callback.NoteItemTouchHelperCallback;

import java.util.List;

import static com.elize.notepad_recyclerview.ui.activity.NoteActivityConstants.INVALID_POSITION;
import static com.elize.notepad_recyclerview.ui.activity.NoteActivityConstants.KEY_NOTE;
import static com.elize.notepad_recyclerview.ui.activity.NoteActivityConstants.KEY_POSITION;
import static com.elize.notepad_recyclerview.ui.activity.NoteActivityConstants.REQUEST_CODE_NEW_NOTE;
import static com.elize.notepad_recyclerview.ui.activity.NoteActivityConstants.REQUEST_CODE_UPDATE_NOTE;


public class NoteListActivity extends AppCompatActivity {

    private static final String APPBAR_TITLE = "Notes";
    private NoteListAdapter noteListAdapter;
    private final NoteDAO noteDAO = new NoteDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        setTitle(APPBAR_TITLE);
        List<Note> noteList = fillNotes();
        configureRecyclerView(noteList);
        configureAddNote();
    }

    private void configureAddNote() {
        TextView textViewAddNote = findViewById(R.id.note_list_add_note);
        textViewAddNote.setOnClickListener(view -> openNoteActivityAdd());
    }

    private void openNoteActivityAdd() {
        Intent openNoteActivity =
                new Intent(NoteListActivity.this, NoteActivity.class);
        startActivityForResult(openNoteActivity, REQUEST_CODE_NEW_NOTE);
    }

    private List<Note> fillNotes() {
        return noteDAO.listAll();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (isResultNewNote(requestCode, data)) {
            if (isResultCodeOk(resultCode)) {
                Note newNote = data.getParcelableExtra(KEY_NOTE);
                add(newNote);
            }
        } else if (isResultUpdateNote(requestCode, data)) {
            if (isResultCodeOk(resultCode)) {
                Note updatedNote = data.getParcelableExtra(KEY_NOTE);
                int updatedNotePosition = data.getIntExtra(KEY_POSITION, INVALID_POSITION);
                if (isValidPosition(updatedNotePosition)) {
                    update(updatedNote, updatedNotePosition);
                } else {
                    Toast.makeText(this, "Error during note update", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void update(Note updatedNote, int updatedNotePosition) {
        noteDAO.update(updatedNotePosition, updatedNote);
        noteListAdapter.update(updatedNotePosition, updatedNote);
    }

    private boolean isValidPosition(int updatedNotePosition) {
        return updatedNotePosition > INVALID_POSITION;
    }

    private boolean isResultUpdateNote(int requestCode, @Nullable Intent data) {
        return isRequestCodeUpdateNote(requestCode)
                && hasNote(data);
    }

    private boolean isRequestCodeUpdateNote(int requestCode) {
        return requestCode == REQUEST_CODE_UPDATE_NOTE;
    }

    private void add(Note newNote) {
        noteDAO.add(newNote);
        noteListAdapter.addNote(newNote);
    }

    private boolean isResultNewNote(int requestCode, @Nullable Intent data) {
        return isRequestCodeNewNote(requestCode) &&
                hasNote(data);
    }

    private boolean hasNote(@Nullable Intent data) {
        return data != null && data.hasExtra(KEY_NOTE);
    }

    private boolean isResultCodeOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean isRequestCodeNewNote(int requestCode) {
        return requestCode == REQUEST_CODE_NEW_NOTE;
    }

    private void configureRecyclerView(List<Note> notes) {
        RecyclerView noteListRecyclerView = findViewById(R.id.note_list_recyclerView);
        configureAdapter(notes, noteListRecyclerView);
        configureItemTouchHelper(noteListRecyclerView);

    }

    private void configureItemTouchHelper(RecyclerView noteListRecyclerView) {
        ItemTouchHelper itemTouchHelper =
                new ItemTouchHelper(new NoteItemTouchHelperCallback(noteListAdapter));
        itemTouchHelper.attachToRecyclerView(noteListRecyclerView);
    }

    private void configureAdapter(List<Note> notes, RecyclerView noteListRecyclerView) {
        noteListAdapter = new NoteListAdapter(notes, this);
        noteListRecyclerView.setAdapter(noteListAdapter);
        noteListAdapter.setOnItemClickListener(this::openNoteActivityUpdate);
    }

    private void openNoteActivityUpdate(Note note, int position) {
        Intent openNoteUpdate = new Intent(this, NoteActivity.class);
        openNoteUpdate.putExtra(KEY_NOTE, note);
        openNoteUpdate.putExtra(KEY_POSITION, position);
        startActivityForResult(openNoteUpdate, REQUEST_CODE_UPDATE_NOTE);
    }
}