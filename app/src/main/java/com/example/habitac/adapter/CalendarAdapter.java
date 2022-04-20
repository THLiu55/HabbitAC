package com.example.habitac.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.habitac.R;
import com.example.habitac.database.Task;
import com.example.habitac.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    List<Task> task = new ArrayList<>();
    Context context;

    public CalendarAdapter(Context context, List<Task> tasks) {
        this.task = tasks;
        this.context = context;
    }
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.location, parent, false);
        context = parent.getContext();
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.taskName.setText(task.get(position).getName());
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.item_come_up));
        holder.editArea.setClickable(false);
    }

    @Override
    public int getItemCount() {
        return task.size();
    }
}