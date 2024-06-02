package com.example.myapplication;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link zhichu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class zhichu extends Fragment {

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

    public zhichu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment zhichu.
     */
    // TODO: Rename and change types and number of parameters
    public static zhichu newInstance(String param1, String param2) {
        zhichu fragment = new zhichu();
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
        View view=inflater.inflate(R.layout.fragment_zhichu, container, false);
        moneynum= view.findViewById(R.id.moneynum);
        keyboardView=view.findViewById(R.id.jianpan);
        jp=new jianpan(keyboardView,moneynum);
        jp.show();

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
                }
                getActivity().finish();
            }
        });
        return view;
    }

}