package com.example.myapplication;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

public class jianpan {
    private KeyboardView keyboardView;
    private EditText editText;
    private Keyboard mykey;//自定义键盘
    public interface OnEnsureListener{
        public void onEnsure();
    }
    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }
    public jianpan(KeyboardView keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;
        this.editText.setInputType(InputType.TYPE_NULL);//不弹出系统键盘
        mykey = new Keyboard(this.editText.getContext(), R.xml.jianpan);

        this.keyboardView.setKeyboard(mykey);//设置要显示键盘的样式
        this.keyboardView.setEnabled(true);
        this.keyboardView.setPreviewEnabled(false);
        this.keyboardView.setOnKeyboardActionListener(listener);
    }
    KeyboardView.OnKeyboardActionListener listener=new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int i) {

        }

        @Override
        public void onRelease(int i) {

        }

        @Override
        public void onKey(int i, int[] ints) {
            Editable editable=editText.getText();
            int start=editText.getSelectionStart();
            if(i==-1){//删除
                if(editable!=null&&editable.length()>0){
                    if(start>0){
                        editable.delete(start-1,start);
                    }
                }
            }
            else if(i==-2){//确定
                onEnsureListener.onEnsure();//接口回调
                System.out.println(editText.getText().toString());
            }
            else if(i==-3){//清零
                editable.clear();
            }
            else{
                editable.insert(start,Character.toString((char)i));
            }
        }

        @Override
        public void onText(CharSequence charSequence) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };
    public void show(){
        //显示键盘
        int now=keyboardView.getVisibility();
        if(now== View.INVISIBLE||now==View.GONE){
            keyboardView.setVisibility(View.VISIBLE);
        }
    }
    public void hide(){
        //隐藏键盘
        int now=keyboardView.getVisibility();
        if(now==View.VISIBLE||now==View.INVISIBLE){
            keyboardView.setVisibility(View.GONE);
        }
    }
}

