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

    @ColumnInfo(name = "active_date")
    public LocalDate activeDate;

    @ColumnInfo(name = "date_created")
    public LocalDate dateCreated;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "type")
    public String type;

    TaskEntity(@NonNull String text, int sortOrder, boolean isFinished, LocalDate activeDate, String category, String type) {
        this.text = text;
        this.sortOrder = sortOrder;
        this.isFinished = isFinished;
        this.activeDate = activeDate;
        this.dateCreated = activeDate;
        this.category = category;
        this.type = type;
    }

    public static TaskEntity fromTask(@NonNull Task task) {
        var card = new TaskEntity(task.text(), task.sortOrder(), task.isFinished(),
                task.activeDate(), task.category(), task.type() );
        card.id = task.id();
        return card;
    }

    public @NonNull Task toTask() {
        return new Task(id, text, sortOrder, isFinished, activeDate, category, type);
    }
}
