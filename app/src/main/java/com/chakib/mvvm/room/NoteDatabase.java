package com.chakib.mvvm.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.chakib.mvvm.dao.NoteDao;
import com.chakib.mvvm.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class  NoteDatabase extends RoomDatabase {

    //turn this class into siglethon (we can't create multiple instance of this DB => we use the same instance anywhere in our app
    private static NoteDatabase instance;

    //access the DB operation method that we added to the noteDao interface
    public abstract NoteDao noteDao();

    // synchronized mean that only one thread at the time can access this methode this value dont accidently create two instance of the DB when two different threads try to access
    // this methode at the same time because this can happen in a multithread environment
    public static synchronized NoteDatabase getInstance(Context context)
    {
        if (instance == null)
        {
            // return an instance of the DB
            instance = Room.databaseBuilder(context.getApplicationContext(),
                     NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }




    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db)
        {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };




    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private NoteDao noteDao;

        public PopulateDbAsyncTask(NoteDatabase db)
        {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            noteDao.insert(new Note("Tiltle 1","Description 1",1));
            noteDao.insert(new Note("Tiltle 2","Description 2",2));
            noteDao.insert(new Note("Tiltle 3","Description 3",3));
            return null;
        }
    }


}
