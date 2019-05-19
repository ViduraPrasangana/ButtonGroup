package com.hunteralex.buttonsgroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ButtonItem {
    private View view;
    private RelativeLayout background;
    private TextView text;
    public ButtonItem(Context context){
        this(LayoutInflater.from(context).inflate(R.layout.button_item,null));
    }
    public ButtonItem(View view){
        this.view = view;
        this.background = view.findViewById(R.id.view);
        this.text = view.findViewById(R.id.text);
    }

    public RelativeLayout getBackground() {
        return background;
    }

    public void setBackground(RelativeLayout background) {
        this.background = background;
    }

    public View getView() {
        return view;
    }
    public View getView1() {
        return background;
    }

    public void setView(RelativeLayout view) {
        this.view = view;
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }
}
