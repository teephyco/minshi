package com.yalianxun.mingshi.others;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;

import com.yalianxun.mingshi.R;

public class YLXTextWatcher implements TextWatcher {
    private ImageView imageView;
    private int default_img;
    private int highlight_img;

    public YLXTextWatcher(ImageView imageView, int default_img, int highlight_img) {
        this.imageView = imageView;
        this.default_img = default_img;
        this.highlight_img = highlight_img;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if(s != null && count == 0){
            imageView.setImageResource(highlight_img);
        }
        if(s != null && s.length()  < 2 && count == 1) {
            imageView.setImageResource(default_img);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
