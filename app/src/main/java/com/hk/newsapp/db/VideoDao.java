package com.hk.newsapp.db;

import com.hk.newsapp.model.Video;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Video... videos);

    @Query("SELECT * FROM video")
    List<Video> getAllVideos();

    @Query("SELECT * FROM video WHERE newsItemId=:newsItemId")
    List<Video> getVideosForNewsItem(long newsItemId);

    @Delete
    void delete(Video... videos);

    @Query("DELETE FROM video")
    void deleteAll();
}
