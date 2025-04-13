package com.example.gymlog_project.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gymlog_project.database.Entities.GymLog;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface GymLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GymLog gymlog);

    @Query("SELECT * FROM " + GymLogDatabase.GYM_LOG_TABLE + " ORDER BY date DESC")
    List<GymLog> getAllRecords();
}
