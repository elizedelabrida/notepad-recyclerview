package com.elize.notepad_recyclerview.ui.recycler.helper.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.elize.notepad_recyclerview.dao.NoteDAO;
import com.elize.notepad_recyclerview.ui.recycler.adapter.NoteListAdapter;

public class NoteItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private final NoteListAdapter adapter;

    public NoteItemTouchHelperCallback(NoteListAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        int dragFlags = ItemTouchHelper.DOWN | ItemTouchHelper.UP
                | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @
            NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int initialPosition = viewHolder.getAdapterPosition();
        int endPosition = target.getAdapterPosition();
        swapNotes(initialPosition, endPosition);
        return true;
    }

    private void swapNotes(int initialPosition, int endPosition) {
        new NoteDAO().swap(initialPosition, endPosition);
        adapter.swap(initialPosition, endPosition);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int notePosition = viewHolder.getAdapterPosition();
        removeNote(notePosition);
    }

    private void removeNote(int position) {
        new NoteDAO().remove(position);
        adapter.remove(position);
    }
}
