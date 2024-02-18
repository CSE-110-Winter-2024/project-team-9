package edu.ucsd.cse110.successorator.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters({LocalDateConverter.class})
@Database(entities = {TaskEntity.class}, version = 2)
public abstract class SuccessoratorDatabase extends RoomDatabase{
    public abstract TaskDao taskDao();
}