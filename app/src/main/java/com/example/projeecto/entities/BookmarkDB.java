package com.example.projeecto.entities;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.projeecto.DAO.BookmarkDAO;

@Database(entities = Parts.class,version = 5 )
public abstract class BookmarkDB  extends RoomDatabase {

    private static BookmarkDB instance;

    public abstract BookmarkDAO BookmarkDao();

    public static synchronized BookmarkDB getInstance(Context context){

        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),BookmarkDB.class,"bookmark_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();

        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            new PopulateDatabaseAsyncTask(instance).execute();
            super.onCreate(db);
        }
    };

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private BookmarkDAO bookmarkDataAccessObject;

        private PopulateDatabaseAsyncTask(BookmarkDB bookmarkDataAccessObject) {
            this.bookmarkDataAccessObject = bookmarkDataAccessObject.BookmarkDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*bookmarkDataAccessObject.insert(new Parts("Rx7","Mazda",6));
            bookmarkDataAccessObject.insert(new Parts("Miata","Mazda",4));
            bookmarkDataAccessObject.insert(new Cars("Ford Gt","Ford",5));*/
            return null;
        }
    }
}
