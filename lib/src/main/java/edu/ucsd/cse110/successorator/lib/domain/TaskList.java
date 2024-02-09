package edu.ucsd.cse110.successorator.lib.domain;

import java.util.List;

import edu.ucsd.cse110.successorator.lib.util.Subject;

public interface TaskList {
    Subject<Task> find(int id);

    Subject<List<Task>> findAll();

    void save(List<Task> tasks);

    void remove(int id);

    void add(Task task);

}