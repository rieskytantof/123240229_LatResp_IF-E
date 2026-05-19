/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.pbo.latres.model;

import com.pbo.latres.dto.InsertTodoDTO;
import java.util.List;

/**
 *
 * @author farhannivta
 */
public interface TodoRepository {
    public List<TodoTask> getAll();
    
    public TodoTask getById(int id);
    
    public Boolean insert(InsertTodoDTO insertTodoDTO);
    
    public Boolean update(TodoTask todoTask);
    
    public Boolean deleteById(int id);
}
