package com.elize.notepad_recyclerview.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private final String title;
    private final String description;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public static final Parcelable.Creator<Note>
            CREATOR = new Parcelable.Creator<Note>() {

        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    private Note(Parcel in) {
        title = in.readString();
        description = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeString(description);
    }
}