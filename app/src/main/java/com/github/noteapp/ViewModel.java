package com.github.noteapp;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Note>> allNotes;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note) {
        repository.insert(note);
    }
    public void update(Note note) {
        repository.update(note);
    }
    public void delete(Note note) {
        repository.delete(note);
    }
    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}

