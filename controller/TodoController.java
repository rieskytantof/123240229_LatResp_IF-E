/*
 * Controller pada pola MVC.
 * Memindahkan seluruh logika bisnis dari Latres.java ke sini.
 */
package com.pbo.latres.controller;

import com.pbo.latres.dto.InsertTodoDTO;
import com.pbo.latres.model.TodoRepository;
import com.pbo.latres.model.TodoTask;
import com.pbo.latres.view.TodoView;

/**
 * TodoController bertindak sebagai perantara antara View dan Model (Repository).
 *
 * Tanggung jawab:
 *  - Menangani semua event dari View (tombol Add, Update, Delete, Clear, dan seleksi tabel).
 *  - Memanggil operasi yang sesuai pada Repository (Model).
 *  - Memperbarui View setelah setiap operasi.
 *
 * Dengan memindahkan logika ke sini, Latres.java (entry point) hanya perlu
 * membuat instance Controller dan semua koneksi event sudah terkelola di sini.
 */
public class TodoController {

    private final TodoRepository repository;
    private final TodoView view;

    public TodoController(TodoRepository repository, TodoView view) {
        this.repository = repository;
        this.view = view;
        bindEvents();
        refreshTable();
    }

    /** Menghubungkan semua event listener dari View ke handler di Controller ini. */
    private void bindEvents() {

        view.onAdd(e -> handleAdd());

        view.onUpdate(e -> handleUpdate());

        view.onDelete(e -> handleDelete());

        view.onClear(e -> view.clearForm());

        view.onTableSelect(e -> handleTableSelect());
    }

    /** Memuat ulang seluruh data dari repository ke tabel di View. */
    private void refreshTable() {
        view.showTodos(repository.getAll());
    }

    /** Menangani klik tombol Tambah. */
    private void handleAdd() {
        String title  = view.getTitleInput();
        String status = view.getStatusInput();

        if (title.isEmpty()) {
            view.showMessage("Task tidak boleh kosong");
            return;
        }

        repository.insert(new InsertTodoDTO(title, status));
        refreshTable();
        view.clearForm();
    }

    /** Menangani klik tombol Update. */
    private void handleUpdate() {
        int selectedId = view.getSelectedTodoId();

        if (selectedId == -1) {
            view.showMessage("Pilih data terlebih dahulu");
            return;
        }

        TodoTask task = new TodoTask(
            selectedId,
            view.getTitleInput(),
            view.getStatusInput()
        );

        repository.update(task);
        refreshTable();
        view.clearForm();
    }

    /** Menangani klik tombol Hapus. */
    private void handleDelete() {
        int selectedId = view.getSelectedTodoId();

        if (selectedId == -1) {
            view.showMessage("Pilih data terlebih dahulu");
            return;
        }

        repository.deleteById(selectedId);
        refreshTable();
        view.clearForm();
    }

    /** Mengisi form ketika baris di tabel dipilih. */
    private void handleTableSelect() {
        int selectedId = view.getSelectedTodoId();

        if (selectedId == -1) {
            return;
        }

        TodoTask task = repository.getById(selectedId);

        if (task != null) {
            view.setForm(task);
        }
    }
}
