package com.hunteralex.buttongroup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import android.graphics.drawable.TransitionDrawable;

import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.RelativeLayout;


import java.util.ArrayList;

public class ButtonGroup extends LinearLayout implements View.OnClickListener, View.OnTouchListener {
    public interface OnItemClickListener {
        void OnItemClickListener(int position);
    }

    protected static class Default {
        protected static final int BACKGROUND_COLOR = R.color.gray;
        protected static final int TEXT_COLOR = R.color.white;
        protected static final int BACKGROUND_COLOR_FOCUS = R.color.dark;
        protected static final int BORDER_COLOR = R.color.dark;


        protected static final float CORNER_RADIUS_DP = 10;
        protected static final float TEXT_SIZE = 20;
        protected static final float BUTTON_HEIGHT = 60;

        protected static final boolean BORDER_VISIBILITY = true;

    }

    private OnItemClickListener onItemClickListener = null;

    private final int ANIMATION_DURATION = 50;

    private int backgroundColor;
    private int textColor;
    private int backgroundColorFocus;
    private int borderColorFocus;
    private int borderColor;

    private float cornerRadius;
    private float textSize;
    private float buttonHeight;

    private boolean borderVisibility;

    private LayoutInflater layoutInflater;
    private LinearLayout buttonsContainer;
    private ArrayList<ButtonItem> allButtons;
    private ArrayList<String> strings;
    private Context context;

    private TransitionDrawable tr;


    public ButtonGroup(Context context) {
        this(context, null);
    }

    public ButtonGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ButtonGroupOptions);

            try {
                this.context = context;
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layoutInflater.inflate(R.layout.buttons_container, this, true);

                buttonsContainer = (LinearLayout) findViewById(R.id.container);

                this.backgroundColor = attributes.getColor(R.styleable.ButtonGroupOptions_backgroundColor, ContextCompat.getColor(context, Default.BACKGROUND_COLOR));
                this.textColor = attributes.getColor(R.styleable.ButtonGroupOptions_fontColor, ContextCompat.getColor(context, Default.TEXT_COLOR));
                this.backgroundColorFocus = attributes.getColor(R.styleable.ButtonGroupOptions_backgroundColorFocus, ContextCompat.getColor(context, Default.BACKGROUND_COLOR_FOCUS));
                this.borderColorFocus = attributes.getColor(R.styleable.ButtonGroupOptions_borderColorFocus, ContextCompat.getColor(context, Default.BORDER_COLOR));
                this.borderColor = attributes.getColor(R.styleable.ButtonGroupOptions_borderColor, ContextCompat.getColor(context, Default.BORDER_COLOR));

                this.borderVisibility = attributes.getBoolean(R.styleable.ButtonGroupOptions_borderVisibility, Default.BORDER_VISIBILITY);

                this.buttonHeight = attributes.getDimension(R.styleable.ButtonGroupOptions_buttonHeight, dp2px(context, Default.BUTTON_HEIGHT));
                this.cornerRadius = attributes.getDimension(R.styleable.ButtonGroupOptions_cornerRadius, dp2px(context, Default.CORNER_RADIUS_DP));
                this.textSize = attributes.getDimension(R.styleable.ButtonGroupOptions_android_textSize, dp2px(context, Default.TEXT_SIZE));

            } finally {
                allButtons = new ArrayList<>();
                attributes.recycle();
            }
        }
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getBackgroundColorFocus() {
        return backgroundColorFocus;
    }

    public void setBackgroundColorFocus(int backgroundColorFocus) {
        this.backgroundColorFocus = backgroundColorFocus;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int getBorderColorFocus() {
        return borderColorFocus;
    }

    public void setBorderColorFocus(int borderColorFocus) {
        this.borderColorFocus = borderColorFocus;
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public boolean isBorderVisibility() {
        return borderVisibility;
    }

    public void setBorderVisibility(boolean borderVisibility) {
        this.borderVisibility = borderVisibility;
    }

    public float getButtonHeight() {
        return buttonHeight;
    }

    public void setButtonHeight(float buttonHeight) {
        this.buttonHeight = buttonHeight;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void notifyItemClick(int position) {
        if (onItemClickListener != null) {
            onItemClickListener.OnItemClickListener(position);
        }
    }

    public void addButtons(ArrayList<String> arrayList) {
        strings = arrayList;
        for (String text : arrayList) {
            addButtonItem(text);
        }
    }


    private void addButtonItem(String text) {
        addButtonItem(text, buttonHeight);
    }

    private void addButtonItem(String text, float buttonHeight) {
        ButtonItem buttonItem = new ButtonItem(context);

        buttonItem.getText().setText(text);
        buttonItem.getText().setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        buttonItem.getView().setOnClickListener(this);
        buttonItem.getView().setOnTouchListener(this);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) buttonHeight);
        buttonsContainer.addView(buttonItem.getView(), layoutParams);
        allButtons.add(buttonItem);
        unFocus(buttonItem);
    }

    @Override
    public void onClick(View v) {
        notifyItemClick(buttonsContainer.indexOfChild(v));
    }


    @Override
    public boolean onTouch(final View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            focus(allButtons.get(buttonsContainer.indexOfChild(v)));
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            unFocus(allButtons.get(buttonsContainer.indexOfChild(v)));
        }
        return false;
    }


    private void focus(ButtonItem buttonItem) {
        Drawable drOld = buttonItem.getView().getBackground();
        Drawable drNew = getDrawable(buttonItem, backgroundColorFocus, borderColorFocus);
        tr = new TransitionDrawable(new Drawable[]{drOld, drNew});
        buttonItem.getView().setBackground(tr);
        tr.startTransition(ANIMATION_DURATION);
    }

    private void unFocus(ButtonItem buttonItem) {
        if (tr != null) {
            tr.reverseTransition(ANIMATION_DURATION);
        } else {
            Drawable drNew = getDrawable(buttonItem, backgroundColor, borderColor);
            buttonItem.getView().setBackground(drNew);
        }
    }

    @SuppressLint("ResourceType")
    private Drawable getDrawable(ButtonItem buttonItem, int backgroundColor, int borderColor) {
        GradientDrawable gradientDrawable = makeShape(buttonItem);
        gradientDrawable.setColor(backgroundColor);
        buttonItem.getText().setTextColor(textColor);

        if (borderVisibility) {
            gradientDrawable.setStroke(2, borderColor);
        }

        return gradientDrawable;
    }

    private GradientDrawable makeShape(ButtonItem buttonItem) {
        GradientDrawable gr = new GradientDrawable();
        if (isOnly()) {
            gr.setCornerRadii(new float[]{cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius, cornerRadius});
        } else if (isFirst(buttonItem)) {
            gr.setCornerRadii(new float[]{cornerRadius, cornerRadius, cornerRadius, cornerRadius, 0, 0, 0, 0});
        } else if (isLast(buttonItem)) {
            gr.setCornerRadii(new float[]{0, 0, 0, 0, cornerRadius, cornerRadius, cornerRadius, cornerRadius});
        } else {
            gr.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, 0, 0});
        }
        return gr;

    }

    private boolean isOnly() {
        return strings.size() == 1;
    }

    private boolean isFirst(ButtonItem buttonItem) {
        return strings.indexOf(buttonItem.getText().getText()) == 0;
    }

    private boolean isLast(ButtonItem buttonItem) {
        return strings.indexOf(buttonItem.getText().getText()) == strings.size() - 1;
    }

    private float dp2px(Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

}
