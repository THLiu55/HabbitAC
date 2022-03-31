package com.example.habitac.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitac.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    String[] data1, data2;
    List<Integer> images;
    Context context;
    ItemViewHolder.ItemClickListener itemClickListener;

    public ItemAdapter(Context context, String[] s1, String[] s2, List<Integer> images, ItemViewHolder.ItemClickListener itemClickListener) {
        this.context = context;
        this.data1 = s1;
        this.data2 = s2;
        this.images = images;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_my_item, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view, itemClickListener);



        return (ItemViewHolder) holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);
        holder.imageView.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    // 先构建一个View holder 来管理所有可用 view
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView myText1, myText2;
        ImageView imageView;
        ItemClickListener itemClickListener;

        public ItemViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.MyTextView1);
            myText2 = itemView.findViewById(R.id.textView_notify_clock);
            imageView = itemView.findViewById(R.id.imageView2);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.click(view);
        }

        public interface ItemClickListener {
            // send the position of the clicked item
            void click(View position);
        }
    }
}

