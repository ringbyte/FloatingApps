package com.sj.astin.multimanager.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.os.Bundle;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.provider.Settings;
import android.net.Uri;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.view.WindowManager;
import android.graphics.Color;
import android.util.TypedValue;
import android.graphics.drawable.GradientDrawable;

import com.sj.astin.multimanager.R;
import com.sj.astin.multimanager.ui.FlatButton;
import com.sj.astin.multimanager.ui.MyColor;
import com.sj.astin.multimanager.Utils;



public class SettingActivity extends AppCompatActivity {
    private Context ctx;
    private LinearLayout main;
    private WindowManager wm;
    private int WIDTH;
    private int HEIGHT;
    private int DP;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        
        ctx = getApplicationContext();
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WIDTH = wm.getDefaultDisplay().getWidth();
        HEIGHT = wm.getDefaultDisplay().getHeight();
        DP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, ctx.getResources().getDisplayMetrics());
        
        /*---main layout---*/
        main = new LinearLayout(ctx);
        main.setOrientation(1);
        main.setPadding(15 * DP, 10 * DP, 15 * DP, 5 * DP);
        
        /*---head color setting---*/
        LinearLayout head = new LinearLayout(ctx);
        head.setOrientation(0);
        head.setPadding(15 * DP, 5 * DP, 15 * DP, 5 * DP);
        
        TextView tv = new TextView(ctx);
        tv.setText("Head 색상 바꾸기");
        tv.setTextSize(20);
        tv.setTextColor(Color.BLACK);
        head.addView(tv, WIDTH - 95 * DP, 35 * DP);
        
        Button colorPick = new Button(ctx);
        colorPick.setText("");
        GradientDrawable gradient = new GradientDrawable();
        gradient.setStroke(3 * DP, Color.GRAY);
        gradient.setCornerRadius(500 * DP);
        gradient.setColor(Color.rgb(90, 110, 255));
        colorPick.setBackgroundDrawable(gradient);
        head.addView(colorPick, 35 * DP, 35 * DP);
        
        main.addView(head);
        main.addView(line(Color.GRAY));
        
        setContentView(main);
    }
    
    public TextView line(int color) {
        TextView tv = new TextView(ctx);
        tv.setText("");
        tv.setBackgroundColor(color);
        tv.setLayoutParams(new LinearLayout.LayoutParams(WIDTH - 5 * DP, 2 * DP));
        
        return tv;
    }
}
