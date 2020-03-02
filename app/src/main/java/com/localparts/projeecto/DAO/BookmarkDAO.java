package com.localparts.projeecto.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.localparts.projeecto.entities.Parts;

import java.util.List;

@Dao
public interface BookmarkDAO {

    @Insert
    void insert(Parts deal);

    @Delete
    void delete(Parts deal);

    @Update
    void update(Parts deal);

    @Query("DELETE FROM bookmark_table")
    void deleteAllDeals();

    @Query("SELECT * FROM bookmark_table ORDER BY price")
    LiveData<List<Parts>> getAllDeals();

}
