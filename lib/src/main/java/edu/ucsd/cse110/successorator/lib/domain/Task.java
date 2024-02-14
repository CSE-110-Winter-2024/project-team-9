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

    private final LocalDate dateCreated;

    public Task(@Nullable Integer id, @NonNull String text, int sortOrder, boolean isFinished, LocalDate dateCreated) {
        this.id = id;
        this.text = text;
        this.sortOrder = sortOrder;
        this.isFinished = isFinished;
        this.dateCreated = dateCreated;
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

    public LocalDate dateCreated() {return dateCreated;}

    public Task withId(int id) {
        return new Task(id, text, this.sortOrder, isFinished, dateCreated);
    }

    public Task withSortOrder(int sortOrder) {
        return new Task(this.id, text, sortOrder, isFinished, dateCreated);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(text, task.text)
                && Objects.equals(sortOrder, task.sortOrder)
                && Objects.equals(isFinished, task.isFinished)
                && Objects.equals(dateCreated, task.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, sortOrder, isFinished);
    }
}