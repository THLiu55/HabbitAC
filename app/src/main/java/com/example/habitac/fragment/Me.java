package com.example.habitac.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.habitac.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Me#} factory method to
 * create an instance of this fragment.
 */
public class Me extends Fragment implements RadioGroup.OnCheckedChangeListener{

    RadioGroup rgp_one;
    RadioGroup rgp_two;
    boolean is_select_rgb_one = true;
    TextView student_class;
    TextView student_sex;
    TextView student_ban;
    TextView student_dormitory ;
    TextView student_bed;
    TextView student_name;

    String[] studentName = {"武器名称：剑","防具名称： 头盔","防具名称： 盔甲","防具名称： 战靴","武器名称：弓箭","武器名称：双手斧"};
    int[] equipmentHealth = {0,100,200,100,0,0};
    int[] equipmentAttackValue = {500,0,0,0,400,600};
    int[] equipmentDefenseValue = {0,40,50,30,0,0};
    int[] equipmentAgility = {15,10,20,5,10,20};
    public String userName;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Me() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param
     * @param
     * @return A new instance of fragment Me.
     */
    // TODO: Rename and change types and number of parameters

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shop, container, false);
        rgp_one = root.findViewById(R.id.rgp_one);
        rgp_two = root.findViewById(R.id.rgp_two);
        rgp_one.setOnCheckedChangeListener(this);
        rgp_two.setOnCheckedChangeListener(this);
        // usernameView.setText(userName);
       student_class = root.findViewById(R.id.Weapon_story);
        student_sex = root.findViewById(R.id.Life_value);
        student_ban = root.findViewById(R.id.attack);
        student_dormitory = root.findViewById(R.id.defense);
        student_bed = root.findViewById(R.id.Dodge);
        student_name = root.findViewById(R.id.Trade_name);
        return root;

    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {


        if(radioGroup.getId() == R.id.rgp_one){
            if(!is_select_rgb_one){
                rgp_two.clearCheck();
                is_select_rgb_one = true;
            }
        }
        else if(radioGroup.getId() == R.id.rgp_two) {
            if(is_select_rgb_one){
                rgp_one.clearCheck();
                is_select_rgb_one = false;
            }
        }

        if(i == R.id.Commodity_display1){
            student_name.setText(studentName[0]);
            student_sex.setText(String.format("%s%s", this.getString(R.string.student_sex), equipmentHealth[0]));
            student_ban.setText(String.format("%s%s", this.getString(R.string.student_ban), equipmentAttackValue[0]));
            student_dormitory.setText(String.format("%s%s", this.getString(R.string.student_dormitory), equipmentDefenseValue[0]));
            student_bed.setText(String.format("%s%s", this.getString(R.string.student_bed), equipmentAgility[0]));
            student_class.setText(String.format("%s%s", this.getString(R.string.student_class), this.getString(R.string.class_name)));
        }
        else if(i == R.id.Commodity_display2){
            student_name.setText(studentName[1]);
            student_sex.setText(String.format("%s%s", this.getString(R.string.student_sex), equipmentHealth[1]));
            student_ban.setText(String.format("%s%s", this.getString(R.string.student_ban), equipmentAttackValue[1]));
            student_dormitory.setText(String.format("%s%s", this.getString(R.string.student_dormitory), equipmentDefenseValue[1]));
            student_bed.setText(String.format("%s%s", this.getString(R.string.student_bed), equipmentAgility[1]));
            student_class.setText(String.format("%s%s", this.getString(R.string.student_class), this.getString(R.string.class_name)));
        }
        else if(i == R.id.Commodity_display3){
            student_name.setText(studentName[2]);
            student_sex.setText(String.format("%s%s", this.getString(R.string.student_sex), equipmentHealth[2]));
            student_ban.setText(String.format("%s%s", this.getString(R.string.student_ban), equipmentAttackValue[2]));
            student_dormitory.setText(String.format("%s%s", this.getString(R.string.student_dormitory), equipmentDefenseValue[2]));
            student_bed.setText(String.format("%s%s", this.getString(R.string.student_bed), equipmentAgility[2]));
            student_class.setText(String.format("%s%s", this.getString(R.string.student_class), this.getString(R.string.class_name)));
        }
        else if(i == R.id.Commodity_display4){
            student_name.setText(studentName[3]);
            student_sex.setText(String.format("%s%s", this.getString(R.string.student_sex), equipmentHealth[3]));
            student_ban.setText(String.format("%s%s", this.getString(R.string.student_ban), equipmentAttackValue[3]));
            student_dormitory.setText(String.format("%s%s", this.getString(R.string.student_dormitory), equipmentDefenseValue[3]));
            student_bed.setText(String.format("%s%s", this.getString(R.string.student_bed), equipmentAgility[3]));
            student_class.setText(String.format("%s%s", this.getString(R.string.student_class), this.getString(R.string.class_name)));
        }
        else if(i == R.id.Commodity_display5){
            student_name.setText(studentName[4]);
            student_sex.setText(String.format("%s%s", this.getString(R.string.student_sex), equipmentHealth[4]));
            student_ban.setText(String.format("%s%s", this.getString(R.string.student_ban), equipmentAttackValue[4]));
            student_dormitory.setText(String.format("%s%s", this.getString(R.string.student_dormitory), equipmentDefenseValue[4]));
            student_bed.setText(String.format("%s%s", this.getString(R.string.student_bed), equipmentAgility[4]));
            student_class.setText(String.format("%s%s", this.getString(R.string.student_class), this.getString(R.string.class_name)));
        }
        else if(i == R.id.Commodity_display6){
            student_name.setText(studentName[5]);
            student_sex.setText(String.format("%s%s", this.getString(R.string.student_sex), equipmentHealth[5]));
            student_ban.setText(String.format("%s%s", this.getString(R.string.student_ban), equipmentAttackValue[5]));
            student_dormitory.setText(String.format("%s%s", this.getString(R.string.student_dormitory), equipmentDefenseValue[5]));
            student_bed.setText(String.format("%s%s", this.getString(R.string.student_bed), equipmentAgility[5]));
            student_class.setText(String.format("%s%s", this.getString(R.string.student_class), this.getString(R.string.class_name)));
        }
    }
}