package edu.ucsd.cse110.successorator.lib.domain;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

public class Task implements Serializable {
    private final @Nullable Integer id;
    private final @NonNull String text;
    private final Integer sortOrder;

    private final boolean isFinished;

    private final LocalDate activeDate;

    private final LocalDate dateCreated;

    private final String category;
    private final String type;

    public Task(@Nullable Integer id, @NonNull String text, int sortOrder, boolean isFinished, LocalDate activeDate, LocalDate dateCreated, String category, String type) {
        this.id = id;
        this.text = text;
        this.sortOrder = sortOrder;
        this.isFinished = isFinished;
        this.activeDate = activeDate;
        this.dateCreated = dateCreated;
        this.category = category;
        this.type = type;
    }

    public Task(@Nullable Integer id, @NonNull String text, int sortOrder, boolean isFinished, LocalDate activeDate, String category, String type) {
        this.id = id;
        this.text = text;
        this.sortOrder = sortOrder;
        this.isFinished = isFinished;
        this.activeDate = activeDate;
        this.dateCreated = activeDate;
        this.category = category;
        this.type = type;
    }

    // Constructor with default values for category and type
    public Task(@Nullable Integer id, @NonNull String text, int sortOrder, boolean isFinished, LocalDate activeDate) {
        this(id, text, sortOrder, isFinished, activeDate, "defaultCategory", "defaultType");
    }

    public @Nullable Integer id() {
        return id;
    }

    public @NonNull String text() {
        return text;
    }

    public int sortOrder() {
        return sortOrder;
    }

    public boolean isFinished() {return isFinished;}

    public LocalDate activeDate() {return activeDate;}

    public LocalDate dateCreated() {return dateCreated;}

    public String category() {return category;}

    public String type() {return type;}




    public Task withId(int id) {
        return new Task(id, text, this.sortOrder, isFinished, activeDate, category, type);
    }

    public Task withSortOrder(int sortOrder) {
        return new Task(this.id, text, sortOrder, isFinished, activeDate, category, type);
    }

    public Task withType(String type){
        return new Task(this.id, text, sortOrder, isFinished, activeDate, category, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(text, task.text) && Objects.equals(category,task.category)
                && Objects.equals(activeDate, task.activeDate)
                && Objects.equals(type, task.type());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, sortOrder, isFinished);
    }

}