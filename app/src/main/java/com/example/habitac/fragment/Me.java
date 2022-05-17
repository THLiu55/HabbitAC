package com.example.habitac.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habitac.R;
import com.example.habitac.activity.Login;
import com.example.habitac.database.Item;
import com.example.habitac.database.User;
import com.example.habitac.model.MainViewModel;
import com.example.habitac.model.SharedViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Me#} factory method to
 * create an instance of this fragment.
 */
public class Me extends Fragment implements RadioGroup.OnCheckedChangeListener {
    Button button;
    RadioGroup rgp_one;
    RadioGroup rgp_two;
    boolean is_select_rgb_one = true;
    TextView student_class;
    TextView student_sex;
    TextView student_ban;
    TextView student_dormitory;
    TextView student_bed;
    TextView student_name;
    SharedViewModel sharedViewModel;
    User loggedUser;
    int[] equipmentHealth = {0, 100, 200, 100, 0, 0};
    int[] equipmentAttackValue = {500, 0, 0, 0, 400, 600};
    int[] equipmentDefenseValue = {0, 40, 50, 30, 0, 0};
    int[] equipmentAgility = {15, 10, 20, 5, 10, 20};
    int[] weaponPrice = {1,2,3,4,5,6};
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
        button = root.findViewById(R.id.button_buy);
        // usernameView.setText(userName);
        sharedViewModel = new ViewModelProvider(Login.login).get(SharedViewModel.class);
        loggedUser = sharedViewModel.getUser();
        student_class = root.findViewById(R.id.Weapon_story);
        student_sex = root.findViewById(R.id.Life_value);
        student_ban = root.findViewById(R.id.attack);
        student_dormitory = root.findViewById(R.id.defense);
        student_bed = root.findViewById(R.id.Dodge);
        return root;

    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {


        if (radioGroup.getId() == R.id.rgp_one) {
            if (!is_select_rgb_one) {
                rgp_two.clearCheck();
                is_select_rgb_one = true;
            }
        } else if (radioGroup.getId() == R.id.rgp_two) {
            if (is_select_rgb_one) {
                rgp_one.clearCheck();
                is_select_rgb_one = false;
            }
        }

        if (i == R.id.Commodity_display1) {
            student_sex.setText(String.format("%s%s", this.getString(R.string.student_sex), equipmentHealth[0]));
            student_ban.setText(String.format("%s%s", this.getString(R.string.student_ban), equipmentAttackValue[0]));
            student_dormitory.setText(String.format("%s%s", this.getString(R.string.student_dormitory), equipmentDefenseValue[0]));
            student_bed.setText(String.format("%s%s", this.getString(R.string.student_bed), equipmentAgility[0]));
            student_class.setText(String.format("%s%s", this.getString(R.string.student_class), weaponPrice[0]));
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Item buy = new Item(1, "sword", loggedUser.getObjectId());
                    buy.setAttack(equipmentAttackValue[0]);
                    buy.setAgility(equipmentAgility[0]);
                    buy.setDefense(equipmentDefenseValue[0]);
                    buy.setHealth(equipmentHealth[0]);
                    buy.setOwn(loggedUser.getObjectId());
                    buy.setType("sword");
                    updateItem(buy);
                    loggedUser.setCoin(-1*weaponPrice[0]);
                }
            });


        } else if (i == R.id.Commodity_display2) {
            student_sex.setText(String.format("%s%s", this.getString(R.string.student_sex), equipmentHealth[1]));
            student_ban.setText(String.format("%s%s", this.getString(R.string.student_ban), equipmentAttackValue[1]));
            student_dormitory.setText(String.format("%s%s", this.getString(R.string.student_dormitory), equipmentDefenseValue[1]));
            student_bed.setText(String.format("%s%s", this.getString(R.string.student_bed), equipmentAgility[1]));
            student_class.setText(String.format("%s%s", this.getString(R.string.student_class), weaponPrice[1]));
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Item buy = new Item(1, "Head", loggedUser.getObjectId());
                    buy.setAttack(equipmentAttackValue[1]);
                    buy.setAgility(equipmentAgility[1]);
                    buy.setDefense(equipmentDefenseValue[1]);
                    buy.setHealth(equipmentHealth[1]);
                    buy.setOwn(loggedUser.getObjectId());
                    buy.setType("Head");
                    updateItem(buy);
                    loggedUser.setCoin(-1*weaponPrice[1]);
                }
            });
        } else if (i == R.id.Commodity_display3) {
            student_sex.setText(String.format("%s%s", this.getString(R.string.student_sex), equipmentHealth[2]));
            student_ban.setText(String.format("%s%s", this.getString(R.string.student_ban), equipmentAttackValue[2]));
            student_dormitory.setText(String.format("%s%s", this.getString(R.string.student_dormitory), equipmentDefenseValue[2]));
            student_bed.setText(String.format("%s%s", this.getString(R.string.student_bed), equipmentAgility[2]));
            student_class.setText(String.format("%s%s", this.getString(R.string.student_class), weaponPrice[2]));
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Item buy = new Item(1, "armor", loggedUser.getObjectId());
                    buy.setAttack(equipmentAttackValue[2]);
                    buy.setAgility(equipmentAgility[2]);
                    buy.setDefense(equipmentDefenseValue[2]);
                    buy.setHealth(equipmentHealth[2]);
                    buy.setOwn(loggedUser.getObjectId());
                    buy.setType("armor");
                    updateItem(buy);
                    loggedUser.setCoin(-1*weaponPrice[2]);
                }
            });
        } else if (i == R.id.Commodity_display4) {
            student_sex.setText(String.format("%s%s", this.getString(R.string.student_sex), equipmentHealth[3]));
            student_ban.setText(String.format("%s%s", this.getString(R.string.student_ban), equipmentAttackValue[3]));
            student_dormitory.setText(String.format("%s%s", this.getString(R.string.student_dormitory), equipmentDefenseValue[3]));
            student_bed.setText(String.format("%s%s", this.getString(R.string.student_bed), equipmentAgility[3]));
            student_class.setText(String.format("%s%s", this.getString(R.string.student_class), weaponPrice[3]));
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Item buy = new Item(1, "boots", loggedUser.getObjectId());
                    buy.setAttack(equipmentAttackValue[3]);
                    buy.setAgility(equipmentAgility[3]);
                    buy.setDefense(equipmentDefenseValue[3]);
                    buy.setHealth(equipmentHealth[3]);
                    buy.setOwn(loggedUser.getObjectId());
                    buy.setType("boots");
                    updateItem(buy);
                    loggedUser.setCoin(-1*weaponPrice[3]);
                }
            });
        } else if (i == R.id.Commodity_display5) {
            student_sex.setText(String.format("%s%s", this.getString(R.string.student_sex), equipmentHealth[4]));
            student_ban.setText(String.format("%s%s", this.getString(R.string.student_ban), equipmentAttackValue[4]));
            student_dormitory.setText(String.format("%s%s", this.getString(R.string.student_dormitory), equipmentDefenseValue[4]));
            student_bed.setText(String.format("%s%s", this.getString(R.string.student_bed), equipmentAgility[4]));
            student_class.setText(String.format("%s%s", this.getString(R.string.student_class), weaponPrice[4]));
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Item buy = new Item(1, "arch", loggedUser.getObjectId());
                    buy.setAttack(equipmentAttackValue[4]);
                    buy.setAgility(equipmentAgility[4]);
                    buy.setDefense(equipmentDefenseValue[4]);
                    buy.setHealth(equipmentHealth[4]);
                    buy.setOwn(loggedUser.getObjectId());
                    buy.setType("arch");
                    updateItem(buy);
                    loggedUser.setCoin(-1*weaponPrice[4]);
                }
            });
        } else if (i == R.id.Commodity_display6) {
            student_sex.setText(String.format("%s%s", this.getString(R.string.student_sex), equipmentHealth[5]));
            student_ban.setText(String.format("%s%s", this.getString(R.string.student_ban), equipmentAttackValue[5]));
            student_dormitory.setText(String.format("%s%s", this.getString(R.string.student_dormitory), equipmentDefenseValue[5]));
            student_bed.setText(String.format("%s%s", this.getString(R.string.student_bed), equipmentAgility[5]));
            student_class.setText(String.format("%s%s", this.getString(R.string.student_class), weaponPrice[5]));
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Item buy = new Item(1, "axe", loggedUser.getObjectId());
                    buy.setAttack(equipmentAttackValue[5]);
                    buy.setAgility(equipmentAgility[5]);
                    buy.setDefense(equipmentDefenseValue[5]);
                    buy.setHealth(equipmentHealth[5]);
                    buy.setOwn(loggedUser.getObjectId());
                    buy.setType("axe");
                    updateItem(buy);
                    loggedUser.setCoin(-1*weaponPrice[5]);
                }
            });
        }
    }

    public void updateItem(Item item) {
        item.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Network Error, Please check your Internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}