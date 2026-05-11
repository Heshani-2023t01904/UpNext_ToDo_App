package com.example.upnext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    private ArrayList<String> todoList;
    private OnItemClickListener listener;

    // create a Interface
    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public ToDoAdapter(ArrayList<String> todoList, OnItemClickListener listener) {
        this.todoList = todoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        holder.todoText.setText(todoList.get(position));

        // for click the Edit Icon
        holder.editBtn.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(position);
        });

        // for click the Delete Icon
        holder.deleteBtn.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public static class ToDoViewHolder extends RecyclerView.ViewHolder {
        TextView todoText;
        ImageView editBtn, deleteBtn;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            todoText = itemView.findViewById(R.id.todoText);
            editBtn = itemView.findViewById(R.id.btnEdit);
            deleteBtn = itemView.findViewById(R.id.btnDelete);
        }
    }
}