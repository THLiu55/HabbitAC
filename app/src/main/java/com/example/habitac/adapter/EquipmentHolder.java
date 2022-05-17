package com.example.habitac.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.habitac.R;

public class EquipmentHolder extends RecyclerView.ViewHolder {
    ImageView photo;
    TextView HP, attack, defense, agility, price;

    public EquipmentHolder(@NonNull View itemView) {
        super(itemView);
        photo = itemView.findViewById(R.id.photo);
        HP = itemView.findViewById(R.id.Life_value);
        attack = itemView.findViewById(R.id.attack);
        defense = itemView.findViewById(R.id.defense);
        agility = itemView.findViewById(R.id.Dodge);
        price = itemView.findViewById(R.id.price);
    }
}
