package edu.ucsd.cse110.successorator.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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

    TaskEntity(@NonNull String text, int sortOrder) {
        this.text = text;
        this.sortOrder = sortOrder;
    }

    public static TaskEntity fromTask(@NonNull Task task) {
        var card = new TaskEntity(task.text(), task.sortOrder());
        card.id = task.id();
        return card;
    }

    public @NonNull Task toTask() {
        return new Task(id, text, sortOrder);
    }
}
