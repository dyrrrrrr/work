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
        boolean isDecimalPoint = true; // 新增布尔变量，用于跟踪小数点是否已输入
        @Override
        public void onKey(int i, int[] ints) {
            int start = editText.getSelectionStart();
            Editable editable = editText.getText();
            int end = editable.length();
            if (i == -3) { // 清零键
                editable.replace(0, editable.length(), "0.00");
                isDecimalPoint = true;
            } else if (i == -1) { // 删除键
                if(Float.parseFloat(editable.toString())!=0) {
                    if (editable.length() > 0) {
                        if (end > 0) {
                            if(editable.toString().indexOf(".")==end-1){
                                isDecimalPoint = false;
                            }
                            editable.delete(end - 1, end);
                        } else {
                            editable.clear();
                            isDecimalPoint = false;
                        }
                    }
                    if (editable.length() == 0) {
                        editable.append("0.00");
                        isDecimalPoint = true;
                    }
                }
            } else if (i == -2) { // 确定键
                onEnsureListener.onEnsure();
            } else if (i == '.') { // 小数点键
                if (!isDecimalPoint&&!editable.toString().equals("0.00")) { // 小数点只能有一个
                    editable.insert(end, ".");
                    isDecimalPoint = true; // 更新小数点标志
                }
            }else {
                if(editable.toString().equals("0.00")){
                    isDecimalPoint = false;
                    editText.setText(Character.toString((char)i));
                } else if (Float.parseFloat(editable.toString())==0&&!editable.toString().contains(".")) {
                    isDecimalPoint = false;
                    editText.setText(Character.toString((char)i));
                } else{
                    if (!editable.toString().contains(".")){//整数
                        editable.insert(end,Character.toString((char)i));
                    }else if(editable.length() - editable.toString().indexOf(".") < 3){//小数点后两位
                        editable.insert(end,Character.toString((char)i));
                    }
                }
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

