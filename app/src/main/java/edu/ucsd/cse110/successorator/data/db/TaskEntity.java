package edu.ucsd.cse110.successorator.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

import edu.ucsd.cse110.successorator.lib.domain.Task;

@Entity(tableName = "tasks")
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id = null;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "sort_order")
    public int sortOrder;

    @ColumnInfo(name = "is_finished")
    public boolean isFinished;

    @ColumnInfo(name = "date_created")
    public LocalDate dateCreated;

    TaskEntity(@NonNull String text, int sortOrder, boolean isFinished, LocalDate dateCreated) {
        this.text = text;
        this.sortOrder = sortOrder;
        this.isFinished = isFinished;
        this.dateCreated = dateCreated;
    }

    public static TaskEntity fromTask(@NonNull Task task) {
        var card = new TaskEntity(task.text(), task.sortOrder(), task.isFinished(), task.activeDate());
        card.id = task.id();
        return card;
    }

    public @NonNull Task toTask() {
        return new Task(id, text, sortOrder, isFinished, dateCreated);
    }
}
