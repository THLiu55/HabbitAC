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
    ImageView image;
    TextView  taskName ;
    View editArea;
    View itemView;
    public CalendarViewHolder(@NonNull View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.imageView_calendar);
        taskName = itemView.findViewById(R.id.taskName_calendar);
        editArea = itemView.findViewById(R.id.edit_area_2);
        this.itemView = itemView.findViewById(R.id.card_view_2);
    }
}
