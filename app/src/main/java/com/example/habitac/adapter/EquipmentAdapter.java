package com.example.habitac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitac.R;
import com.example.habitac.database.Item;

import java.util.ArrayList;
import java.util.List;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentHolder> {
    List<Item> equipment = new ArrayList<>();
    Context context;

   public void setEquipment(List<Item> equipment){
       this.equipment = equipment;
   }


    @NonNull
    @Override
    public EquipmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.equipment_card, parent, false);
        context = parent.getContext();
        return new EquipmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipmentHolder holder, int position) {
        holder.HP.setText(equipment.get(position).getHealth());
        holder.attack.setText(equipment.get(position).getAttack());
        holder.defense.setText(equipment.get(position).getDefense());
        holder.agility.setText(equipment.get(position).getAgility());
        // TODO: Add a price attribute;
    }

    @Override
    public int getItemCount() {
        return equipment.size();
    }
}
