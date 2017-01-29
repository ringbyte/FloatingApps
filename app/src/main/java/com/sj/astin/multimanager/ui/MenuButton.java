package com.sj.astin.multimanager.ui;

import android.widget.ToggleButton;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.GradientDrawable;
import android.content.Context;


public class MenuButton {
    private ToggleButton btn;
    
    public MenuButton(Context ctx) {
        this.btn = new ToggleButton(ctx);
        btn.setChecked(false);
    }
}
