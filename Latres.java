/*
 * Entry point aplikasi Todo List.
 * FILE INI DIMODIFIKASI untuk menerapkan pola MVC dan koneksi MySQL.
 */
package com.pbo.latres;

import com.pbo.latres.config.DatabaseConfig;
import com.pbo.latres.controller.TodoController;
import com.pbo.latres.model.MysqlTodoRepository;
import com.pbo.latres.model.TodoRepository;
import com.pbo.latres.view.TodoView;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Latres — entry point aplikasi.
 *
 * Perubahan dari versi sebelumnya:
 *  1. Repository diganti dari FakeTodoRepository → MysqlTodoRepository
 *     sehingga data tersimpan persisten di MySQL.
 *  2. Seluruh logika event (onAdd, onUpdate, onDelete, dst.) dipindahkan
 *     ke TodoController sesuai pola MVC. Main hanya bertanggung jawab
 *     melakukan wiring (membuat instance dan menghubungkannya).
 */
public class Latres {

    public static void main(String[] args) {
        try {
            // 1. Buat koneksi ke database MySQL
            Connection connection = DatabaseConfig.getConnection();

            // 2. Pastikan tabel 'todos' sudah ada
            DatabaseConfig.initializeTable(connection);

            // 3. Buat repository berbasis MySQL (Model)
            TodoRepository repository = new MysqlTodoRepository(connection);

            // 4. Buat View
            TodoView view = new TodoView();

            // 5. Buat Controller — menghubungkan View dan Model
            new TodoController(repository, view);

        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(
                null,
                "Gagal terhubung ke database MySQL.\n"
                + "Pastikan MySQL berjalan dan konfigurasi di DatabaseConfig.java sudah benar.\n\n"
                + "Detail: " + e.getMessage(),
                "Database Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
