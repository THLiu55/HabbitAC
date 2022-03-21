package com.example.habitac.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitac.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    String[] data1, data2;
    int[] images;
    Context context;

    public ItemAdapter(Context context, String[] s1, String[] s2, int[] images) {
        this.context = context;
        this.data1 = s1;
        this.data2 = s2;
        this.images = images;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_my_item, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        return (ItemViewHolder) holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);
        holder.imageView.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    // 先构建一个View holder 来管理所有可用 view
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView myText1, myText2;
        ImageView imageView;
        View click;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.MyTextView1);
            myText2 = itemView.findViewById(R.id.MyTextView2);
            imageView = itemView.findViewById(R.id.MyImageView);
        }
    }
}

