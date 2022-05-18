package com.example.habitac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitac.R;
import com.example.habitac.database.Item;
import com.example.habitac.fragment.EquipmentFragment;

import java.util.ArrayList;
import java.util.List;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.EquipmentHolder> {
    List<Item> equipment = new ArrayList<>();
    Context context;

    public void setEquipment(List<Item> equipment){
       this.equipment = equipment;
   }
    public List<Item> getEquipment(){
        return this.equipment;
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
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });// TODO: Add a price attribute;
    }

    @Override
    public int getItemCount() {
        return equipment.size();
    }

    class EquipmentHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView HP, attack, defense, agility, price;
        CheckBox checkBox;
        View equipmentView;

        public EquipmentHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            HP = itemView.findViewById(R.id.Life_value);
            attack = itemView.findViewById(R.id.attack);
            defense = itemView.findViewById(R.id.defense);
            agility = itemView.findViewById(R.id.Dodge);
            price = itemView.findViewById(R.id.price);
            checkBox = itemView.findViewById(R.id.checkbox_item);
            equipmentView = itemView.findViewById(R.id.equipment_card);
        }
    }


}