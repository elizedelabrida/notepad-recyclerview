package com.elize.notepad_recyclerview.dao;

import com.elize.notepad_recyclerview.model.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NoteDAO {

    private final static ArrayList<Note> notes = new ArrayList<>();

    public List<Note> listAll() {
        return (List<Note>) notes.clone();
    }

    public void add(Note... notes) {
        NoteDAO.notes.addAll(Arrays.asList(notes));
    }

    public void update(int posicao, Note note) {
        notes.set(posicao, note);
    }

    public void remove(int position) {
        notes.remove(position);
    }

    public void swap(int initialPosition, int endPosition) {
        Collections.swap(notes, initialPosition, endPosition);
    }

    public void removeAll() {
        notes.clear();
    }
}
