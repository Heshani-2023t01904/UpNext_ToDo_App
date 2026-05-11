package com.example.upnext;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class ToDoActivity extends AppCompatActivity {

    // 1. First, declare the variables at the class level
    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton btnAddTodo;
    private ExtendedFloatingActionButton btnUserInfo;
    private ArrayList<String> todoList;
    private ToDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Force light mode for consistency
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);

        // 2. Set the layout before finding views
        setContentView(R.layout.activity_todo);

        // 3. Initialize the variables using findViewById
        recyclerView = findViewById(R.id.todoRecyclerView);
        btnAddTodo = findViewById(R.id.btnAddTodo);
        btnUserInfo = findViewById(R.id.btnUserInfo);

        // Setting up LayoutManager for RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoList = new ArrayList<>();

        // Default data for display
        todoList.add("Plan the Weekly Goals");
        todoList.add("Buy Fresh Fruits");
        todoList.add("Laundry & Ironing");

        // 4. Initialize Adapter with the listener interface
        adapter = new ToDoAdapter(todoList, new ToDoAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                showTodoDialog(true, position);
            }

            @Override
            public void onDeleteClick(int position) {
                todoList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });

        recyclerView.setAdapter(adapter);

        // Navigate to User Info
        btnUserInfo.setOnClickListener(v -> {
            Intent intent = new Intent(ToDoActivity.this, UserInfoActivity.class);
            startActivity(intent);
        });

        // Open Add Task dialog
        btnAddTodo.setOnClickListener(v -> showTodoDialog(false, -1));
    }

    // Custom dialog logic for Add/Edit tasks
    private void showTodoDialog(boolean isEdit, int position) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_todo, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(dialogView).create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        EditText input = dialogView.findViewById(R.id.todoInput);
        Button btnOk = dialogView.findViewById(R.id.btnOk);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        if (isEdit) {
            input.setText(todoList.get(position));
        }

        btnOk.setOnClickListener(v -> {
            String task = input.getText().toString().trim();
            if (!task.isEmpty()) {
                if (isEdit) {
                    todoList.set(position, task);
                    adapter.notifyItemChanged(position);
                } else {
                    todoList.add(task);
                    adapter.notifyItemInserted(todoList.size() - 1);
                    recyclerView.scrollToPosition(todoList.size() - 1);
                }
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Task name cannot be empty!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}