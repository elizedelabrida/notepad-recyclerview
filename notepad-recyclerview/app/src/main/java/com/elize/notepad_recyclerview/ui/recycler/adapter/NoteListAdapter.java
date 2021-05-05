package com.elize.notepad_recyclerview.ui.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elize.notepad_recyclerview.R;
import com.elize.notepad_recyclerview.model.Note;
import com.elize.notepad_recyclerview.ui.recycler.adapter.listener.OnItemClickListener;

import java.util.Collections;
import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {
    private final List<Note> notes;
    private final Context context;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public NoteListAdapter(List<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public NoteListAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View createdView = LayoutInflater.from(context)
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(createdView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.add(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void update(int position, Note note) {
        notes.set(position, note);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        notes.remove(position);
        notifyItemRemoved(position);
    }

    public void swap(int initialPosition, int endPosition) {
        Collections.swap(notes, initialPosition, endPosition);
        notifyItemMoved(initialPosition, endPosition);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewDescription;
        private Note note;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.item_note_title);
            textViewDescription = itemView.findViewById(R.id.item_note_description);
            itemView.setOnClickListener(view -> onItemClickListener.OnItemClick(note, getAdapterPosition()));
        }

        public void add(Note note) {
            fulfillView(note);
        }

        private void fulfillView(Note note) {
            this.note = note;
            textViewTitle.setText(note.getTitle());
            textViewDescription.setText(note.getDescription());
        }
    }
    public void addNote(Note note) {
        notes.add(note);
        notifyDataSetChanged();
    }

}
