package edu.ucsd.cse110.successorator.data.db;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters({LocalDateConverter.class})
@Database(entities = {TaskEntity.class}, version = 3
//          ENABLE AFTER NEXT DATABASE CHANGE 
//        ,
//        autoMigrations = {
//        @AutoMigration(from = 3, to = 4)
//}
)
public abstract class SuccessoratorDatabase extends RoomDatabase{
    public abstract TaskDao taskDao();
}