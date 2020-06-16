package com.github.noteapp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class SqliteDatabase extends RoomDatabase {

    private static SqliteDatabase instance;
    public abstract DaoService daoService();

    public static synchronized SqliteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SqliteDatabase.class, "database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private DaoService daoService;
        private PopulateDbAsyncTask(SqliteDatabase db) {
            daoService = db.daoService();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            daoService.insert(new Note("Title 1", "Description 1", "2020-22-19"));
            daoService.insert(new Note("Title 2", "Description 2", "2020-22-19"));
            daoService.insert(new Note("Title 3", "Description 3", "2020-22-19"));
            return null;
        }
    }
}
