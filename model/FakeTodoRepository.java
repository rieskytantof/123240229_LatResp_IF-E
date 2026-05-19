/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.latres.model;

import com.pbo.latres.dto.InsertTodoDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author farhannivta
 */
public class FakeTodoRepository implements TodoRepository {
    private TodoTask[] tasks = new TodoTask[100];

    private int size = 0;
    private int nextId = 1;

    @Override
    public List<TodoTask> getAll() {
        List<TodoTask> result = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            result.add(tasks[i]);
        }

        return result;
    }

    @Override
    public TodoTask getById(int id) {

        for (int i = 0; i < size; i++) {

            if (tasks[i].getId() == id) {
                return tasks[i];
            }

        }

        return null;
    }

    @Override
    public Boolean insert(InsertTodoDTO insertTodoDTO) {

        if (size >= tasks.length) {
            return false;
        }

        TodoTask newTask = new TodoTask(
            nextId++,
            insertTodoDTO.getTitle(),
            insertTodoDTO.getStatus()
        );

        tasks[size] = newTask;
        size++;

        return true;
    }

    @Override
    public Boolean update(TodoTask todoTask) {

        for (int i = 0; i < size; i++) {

            if (tasks[i].getId() == todoTask.getId()) {

                tasks[i].setTitle(todoTask.getTitle());
                tasks[i].setStatus(todoTask.getStatus());

                return true;
            }

        }

        return false;
    }

    @Override
    public Boolean deleteById(int id) {

        for (int i = 0; i < size; i++) {

            if (tasks[i].getId() == id) {

                for (int j = i; j < size - 1; j++) {
                    tasks[j] = tasks[j + 1];
                }

                tasks[size - 1] = null;

                size--;

                return true;
            }

        }

        return false;
    }
}