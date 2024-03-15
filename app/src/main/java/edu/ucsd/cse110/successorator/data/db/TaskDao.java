package edu.ucsd.cse110.successorator.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(TaskEntity task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)

    List<Long> insert(List<TaskEntity> tasks);

    @Query("SELECT * FROM tasks WHERE id = :id")
    TaskEntity find(int id);

    @Query("SELECT * FROM tasks ORDER BY sort_order")
    List<TaskEntity> findAll();

    @Query("SELECT * FROM tasks WHERE id = :id")
    LiveData<TaskEntity> findAsLiveData(int id);

    @Query("SELECT * FROM tasks ORDER BY is_finished ASC, CASE WHEN is_finished = 0 THEN CASE category WHEN 'home' THEN 1 WHEN 'work' THEN 2 WHEN 'school' THEN 3 WHEN 'errand' THEN 4 ELSE 5 END ELSE 0 END, sort_order")
    LiveData<List<TaskEntity>> findAllAsLiveData();


    @Query("SELECT COUNT(*) FROM tasks")
    int count();

    @Query("SELECT MIN(sort_order) FROM tasks")
    int getMinSortOrder();

    @Query("SELECT MAX(sort_order) FROM tasks")
    int getMaxSortOrder();

    @Query("UPDATE tasks SET sort_order = sort_order + :by " + "WHERE sort_order >= :from AND sort_order <= :to")
    void shiftSortOrders(int from, int to, int by);

    @Transaction
    default int append(TaskEntity task) {
        var sortOrder = getMaxSortOrder() + 1;
        var newTask = new TaskEntity(
                task.text, sortOrder, task.isFinished, task.activeDate, task.category
                , task.type);
        return Math.toIntExact(insert(newTask));
    }

    @Transaction
    default int prepend(TaskEntity task) {
        shiftSortOrders(getMinSortOrder(), getMaxSortOrder(), 1);
        var sortOrder = getMinSortOrder() - 1;
        var newTask = new TaskEntity(
                task.text, sortOrder, task.isFinished, task.activeDate, task.category
                , task.type);

        return Math.toIntExact(insert(newTask));
    }

    @Transaction
    @Query("UPDATE tasks SET active_date = :newDate WHERE id = :taskId")
    void setActiveDate(int taskId, LocalDate newDate);

    @Transaction
    @Query("UPDATE tasks SET is_finished = :isFinished WHERE id = :taskId")
    void setIsFinished(int taskId, boolean isFinished);

    @Transaction
    @Query("UPDATE tasks SET date_created = :newDate WHERE id = :taskId")
    void setDateCreated(int taskId, LocalDate newDate);

    @Transaction
    @Query("UPDATE tasks SET category=:category WHERE id = :taskId")
    void setCategory(int taskId, String category);

    @Transaction
    @Query("UPDATE tasks SET type = :type WHERE id = :taskId")
    void setType(int taskId, String type);

    @Query("DELETE FROM tasks WHERE id = :id")
    void delete(int id);

    @Query("UPDATE tasks SET active_date = :newDate WHERE is_finished=false")
    void updateTaskDates(LocalDate newDate);

    @Query("SELECT * FROM tasks WHERE is_finished = false")
    List<TaskEntity> getActiveTasks();

    @Query("SELECT * FROM tasks WHERE category= :category")
    List<TaskEntity> getCategory(String category);

    @Query("SELECT * FROM tasks WHERE type= :type")
    List<TaskEntity> getType(String type);

    @Query("DELETE FROM tasks")
    void deleteAllTasks();


}
