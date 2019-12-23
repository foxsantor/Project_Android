package com.example.projeecto.entities;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.projeecto.DAO.BookmarkDAO;

import java.util.List;

public class PartsRepo {

    private BookmarkDAO bookmarkDAO;
    private LiveData<List<Parts>> allparts;

    public PartsRepo (Application application)
    {
        BookmarkDB database = BookmarkDB.getInstance(application);
        bookmarkDAO= database.BookmarkDao();
        allparts = bookmarkDAO.getAllDeals();
    }

    public void insert(Parts part){

        new InsertDealsAsynTask(bookmarkDAO).execute(part);

    }
    public void delete(Parts part){

        new DeleteDealsAsynTask(bookmarkDAO).execute(part);
    }
    public void update(Parts part){

        new UpdateDealsAsynTask(bookmarkDAO).execute(part);
    }
    public void deleteAllDeals(){

        new DeleteAllDealsAsynTask(bookmarkDAO).execute();
    }
    public LiveData<List<Parts>> getAllDeals(){

        return allparts;
    }
    private static class InsertDealsAsynTask extends AsyncTask<Parts,Void,Void>
    {
        private BookmarkDAO bookmarkDAO;
        private InsertDealsAsynTask(BookmarkDAO bookmarkDAO)
        {
            this.bookmarkDAO=bookmarkDAO;
        }
        @Override
        protected Void doInBackground(Parts... parts) {
            bookmarkDAO.insert(parts[0]);
            return null;
        }
    }
    private static class DeleteDealsAsynTask extends AsyncTask<Parts,Void,Void>
    {
        private BookmarkDAO bookmarkDAO;
        private DeleteDealsAsynTask(BookmarkDAO bookmarkDAO)
        {
            this.bookmarkDAO=bookmarkDAO;
        }
        @Override
        protected Void doInBackground(Parts... parts) {
            bookmarkDAO.delete(parts[0]);
            return null;
        }
    }
    private static class UpdateDealsAsynTask extends AsyncTask<Parts,Void,Void>
    {
        private BookmarkDAO bookmarkDAO;
        private UpdateDealsAsynTask(BookmarkDAO bookmarkDAO)
        {
            this.bookmarkDAO=bookmarkDAO;
        }
        @Override
        protected Void doInBackground(Parts... parts) {
            bookmarkDAO.update(parts[0]);
            return null;
        }
    }
    private static class DeleteAllDealsAsynTask extends AsyncTask<Void,Void,Void>
    {
        private BookmarkDAO bookmarkDAO;
        private DeleteAllDealsAsynTask(BookmarkDAO bookmarkDAO)
        {
            this.bookmarkDAO=bookmarkDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            bookmarkDAO.deleteAllDeals();
            return null;
        }
    }

}
