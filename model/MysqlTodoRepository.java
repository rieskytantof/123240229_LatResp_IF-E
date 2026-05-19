/*
 * MySQL implementation of TodoRepository
 * Menyimpan data ke database MySQL
 */
package com.pbo.latres.model;

import com.pbo.latres.dto.InsertTodoDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementasi TodoRepository menggunakan MySQL.
 * Menggantikan FakeTodoRepository agar data persisten.
 */
public class MysqlTodoRepository implements TodoRepository {

    private final Connection connection;

    public MysqlTodoRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<TodoTask> getAll() {
        List<TodoTask> result = new ArrayList<>();
        String sql = "SELECT id, title, status FROM todos ORDER BY id ASC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.add(new TodoTask(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public TodoTask getById(int id) {
        String sql = "SELECT id, title, status FROM todos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TodoTask(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean insert(InsertTodoDTO insertTodoDTO) {
        String sql = "INSERT INTO todos (title, status) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, insertTodoDTO.getTitle());
            stmt.setString(2, insertTodoDTO.getStatus());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean update(TodoTask todoTask) {
        String sql = "UPDATE todos SET title = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, todoTask.getTitle());
            stmt.setString(2, todoTask.getStatus());
            stmt.setInt(3, todoTask.getId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteById(int id) {
        String sql = "DELETE FROM todos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
