package com.localparts.projeecto.ViewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.localparts.projeecto.entities.Parts;
import com.localparts.projeecto.entities.PartsRepo;

import java.util.List;

public class BookmarkViewModel extends AndroidViewModel{

        private PartsRepo repository;
        private LiveData<List<Parts>> allParts;


        public BookmarkViewModel(@NonNull Application application) {
            super(application);
            repository = new PartsRepo(application);
            allParts = repository.getAllDeals();
        }

        public void insert(Parts parts)
        {
            repository.insert(parts);
        }
        public void update(Parts parts)
        {
            repository.update(parts);
        }
        public void delete(Parts parts)
        {
            repository.delete(parts);
        }
        public void deleteAll()
        {
            repository.deleteAllDeals();
        }
        public LiveData<List<Parts>> getAllParts()
        {
            return allParts;
        }

    }


