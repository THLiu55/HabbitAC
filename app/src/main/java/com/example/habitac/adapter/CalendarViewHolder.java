package com.example.habitac.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitac.R;

public class CalendarViewHolder extends RecyclerView.ViewHolder{
    TextView  taskName ;
    View editArea;
    View itemView;
    View classifyBar;
    public CalendarViewHolder(@NonNull View itemView) {
        super(itemView);
        taskName = itemView.findViewById(R.id.taskName_calendar);
        editArea = itemView.findViewById(R.id.edit_area_2);
        this.itemView = itemView.findViewById(R.id.card_view_2);
        this.classifyBar = itemView.findViewById(R.id.classify_bar);
    }
}
