/*
 * Helper untuk membuat koneksi ke database MySQL
 */
package com.pbo.latres.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Mengelola koneksi ke database MySQL dan inisialisasi tabel.
 */
public class DatabaseConfig {

    // Sesuaikan konfigurasi ini dengan environment lokal Anda
    private static final String HOST     = "localhost";
    private static final String PORT     = "3306";
    private static final String DATABASE = "todo_app";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static final String URL =
        "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
        + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    /**
     * Membuka dan mengembalikan koneksi ke MySQL.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver tidak ditemukan.", e);
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * Membuat tabel 'todos' jika belum ada.
     * Dipanggil sekali saat aplikasi pertama kali dijalankan.
     */
    public static void initializeTable(Connection connection) throws SQLException {
        String sql =
            "CREATE TABLE IF NOT EXISTS todos ("
            + "  id     INT          NOT NULL AUTO_INCREMENT,"
            + "  title  VARCHAR(255) NOT NULL,"
            + "  status VARCHAR(50)  NOT NULL DEFAULT 'Belum Selesai',"
            + "  PRIMARY KEY (id)"
            + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }
}
