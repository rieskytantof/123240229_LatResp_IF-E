/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.latres.view;

import com.pbo.latres.model.TodoTask;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class TodoView extends JFrame {

    private JTextField txtTitle;
    private JComboBox<String> cbStatus;

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;

    public TodoView() {
        configureFrame();
    }

    private void configureFrame() {

        setTitle("Todo List App");

        setSize(600, 400);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {

        JPanel formPanel = new JPanel(
            new GridLayout(2, 2, 10, 10)
        );

        formPanel.add(new JLabel("Task"));

        txtTitle = new JTextField();

        formPanel.add(txtTitle);

        formPanel.add(new JLabel("Status"));

        cbStatus = new JComboBox<>(new String[]{
            "Belum Selesai",
            "Selesai"
        });

        formPanel.add(cbStatus);

        btnAdd = new JButton("Tambah");

        btnUpdate = new JButton("Update");

        btnDelete = new JButton("Hapus");

        btnClear = new JButton("Clear");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        tableModel = new DefaultTableModel();

        tableModel.addColumn("ID");
        tableModel.addColumn("Task");
        tableModel.addColumn("Status");

        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(
            new BorderLayout()
        );

        topPanel.add(formPanel, BorderLayout.CENTER);

        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        add(scrollPane, BorderLayout.CENTER);
    }

    public String getTitleInput() {
        return txtTitle.getText();
    }

    public String getStatusInput() {
        return cbStatus
            .getSelectedItem()
            .toString();
    }

    public int getSelectedTodoId() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            return -1;
        }

        return Integer.parseInt(
            tableModel
                .getValueAt(selectedRow, 0)
                .toString()
        );
    }

    public void clearForm() {

        txtTitle.setText("");

        cbStatus.setSelectedIndex(0);

        table.clearSelection();
    }

    public void setForm(TodoTask task) {

        txtTitle.setText(task.getTitle());

        cbStatus.setSelectedItem(
            task.getStatus()
        );
    }

    public void showTodos(List<TodoTask> tasks) {

        tableModel.setRowCount(0);

        for (TodoTask task : tasks) {

            Object[] row = {
                task.getId(),
                task.getTitle(),
                task.getStatus()
            };

            tableModel.addRow(row);
        }
    }

    public void showMessage(String message) {

        JOptionPane.showMessageDialog(
            this,
            message
        );
    }

    public void onAdd(ActionListener listener) {
        btnAdd.addActionListener(listener);
    }

    public void onUpdate(ActionListener listener) {
        btnUpdate.addActionListener(listener);
    }

    public void onDelete(ActionListener listener) {
        btnDelete.addActionListener(listener);
    }

    public void onClear(ActionListener listener) {
        btnClear.addActionListener(listener);
    }

    public void onTableSelect(
        ListSelectionListener listener
    ) {

        table.getSelectionModel()
            .addListSelectionListener(listener);
    }
}