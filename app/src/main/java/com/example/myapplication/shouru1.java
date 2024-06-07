package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link shouru1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class shouru1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText moneynum;
    KeyboardView keyboardView;
    jianpan jp;

    public shouru1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment shouru.
     */
    // TODO: Rename and change types and number of parameters
    public static shouru1 newInstance(String param1, String param2) {
        shouru1 fragment = new shouru1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_shouru, container, false);
        moneynum= view.findViewById(R.id.moneynum);
        keyboardView=view.findViewById(R.id.jianpan);



        RadioGroup radioGroup = view.findViewById(R.id.radioGroup1);
        jp=new jianpan(keyboardView,moneynum);
        jp.show();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = group.findViewById(checkedId);
                String selectedType = selectedRadioButton.getText().toString();
            }
        });
        moneynum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s) || s.toString().equals(".")) {
                    moneynum.setText("0.00");
                }
            }
        });

        jp.setOnEnsureListener(new jianpan.OnEnsureListener() {

            @Override
            public void onEnsure() {
                String nowmoney=moneynum.getText().toString();
                if(TextUtils.isEmpty(nowmoney)||nowmoney.equals("0")) {
                    getActivity().finish();
                    return;
                }else{
                    //将数值、类型存在数据库
                    int checkedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = radioGroup.findViewById(checkedId);
                    String selectedType = selectedRadioButton.getText().toString();

                    float amount = Float.parseFloat(nowmoney);
                    int id = getArguments().getInt("ID");

                    manager_money manager=new manager_money(getContext());
                    manager.update(id,amount,selectedType);

                    Intent main=new Intent(getActivity(), viewpager.class);
                    startActivity(main);
                }
                getActivity().finish();
            }
        });
        return view;
    }
}