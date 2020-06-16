package com.github.noteapp;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class Repository {
    private DaoService daoService;
    private LiveData<List<Note>> allNotes;
    public Repository(Application application) {
        SqliteDatabase database = SqliteDatabase.getInstance(application);
        daoService = database.daoService();
        allNotes = daoService.getAllNotes();
    }
    public void insert(Note note) {
        new InsertNoteAsyncTask(daoService).execute(note);
    }
    public void update(Note note) {
        new UpdateNoteAsyncTask(daoService).execute(note);
    }
    public void delete(Note note) {
        new DeleteNoteAsyncTask(daoService).execute(note);
    }
    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(daoService).execute();
    }
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private DaoService daoService;
        private InsertNoteAsyncTask(DaoService daoService) {
            this.daoService = daoService;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            daoService.insert(notes[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private DaoService daoService;
        private UpdateNoteAsyncTask(DaoService daoService) {
            this.daoService = daoService;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            daoService.update(notes[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private DaoService daoService;
        private DeleteNoteAsyncTask(DaoService daoService) {
            this.daoService = daoService;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            daoService.delete(notes[0]);
            return null;
        }
    }
    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private DaoService daoService;
        private DeleteAllNotesAsyncTask(DaoService daoService) {
            this.daoService = daoService;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            daoService.deleteAllNotes();
            return null;
        }
    }
}

