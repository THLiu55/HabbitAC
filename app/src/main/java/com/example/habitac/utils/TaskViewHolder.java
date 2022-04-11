package com.example.habitac.utils;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitac.R;

class TaskViewHolder extends RecyclerView.ViewHolder{
    CheckBox checked;
    TextView taskName;
    View editArea;
<<<<<<< HEAD
=======
    View itemView;
>>>>>>> bcfb4cd (task item add animation~)

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        checked = itemView.findViewById(R.id.task_checked);
        taskName = itemView.findViewById(R.id.taskName);
        editArea = itemView.findViewById(R.id.edit_area);
<<<<<<< HEAD
=======
        this.itemView = itemView.findViewById(R.id.card_view);
>>>>>>> bcfb4cd (task item add animation~)
    }
}
