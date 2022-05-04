package com.example.habitac.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitac.R;

public class RepositoryViewHolder extends RecyclerView.ViewHolder{
    TextView taskName;
    View clickArea;
    View delete;

    public RepositoryViewHolder(@NonNull View itemView) {
        super(itemView);
        taskName = itemView.findViewById(R.id.repository_task_name);
        clickArea = itemView.findViewById(R.id.repository_task_card);
        delete = itemView.findViewById(R.id.repository_delete);
    }
}
