package com.hk.newsapp.db;

import com.hk.newsapp.model.Photo;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Photo... photos);

    @Query("SELECT * FROM photo")
    List<Photo> getAllPhotos();

    @Query("SELECT * FROM photo WHERE newsItemId=:newsItemId")
    List<Photo> getPhotosForNewsItem(long newsItemId);

    @Delete
    void delete(Photo... photos);

    @Query("DELETE FROM photo")
    void deleteAll();
}
