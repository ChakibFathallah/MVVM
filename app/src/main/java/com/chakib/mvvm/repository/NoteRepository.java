package com.chakib.mvvm.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.chakib.mvvm.dao.NoteDao;
import com.chakib.mvvm.model.Note;
import com.chakib.mvvm.room.NoteDatabase;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application)
    {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note)
    {
        new InsertAsyncTask(noteDao).execute(note);
    }

    public void update(Note note)
    {
        new UpdateAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note)
    {
        new DeleteAsyncTask(noteDao).execute(note);

    }

    public void deleteAllNotes()
    {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes()
    {
        return allNotes;
    }








    private static class InsertAsyncTask extends android.os.AsyncTask<Note, Void, Void>
    {
        private NoteDao noteDao;

        private InsertAsyncTask(NoteDao noteDao)
        {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes)
        {
            noteDao.insert(notes[0]);
            return null;
        }

    }

    private static class UpdateAsyncTask extends android.os.AsyncTask<Note, Void, Void>
    {
        private NoteDao noteDao;

        private UpdateAsyncTask(NoteDao noteDao)
        {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes)
        {
            noteDao.update(notes[0]);
            return null;
        }

    }

    private static class DeleteAsyncTask extends android.os.AsyncTask<Note, Void, Void>
    {
        private NoteDao noteDao;

        private DeleteAsyncTask(NoteDao noteDao)
        {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes)
        {
            noteDao.delete(notes[0]);
            return null;
        }

    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao)
        {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            noteDao.deleteAllNotes();
            return null;
        }

    }



}
