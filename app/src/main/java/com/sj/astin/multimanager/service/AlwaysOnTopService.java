package com.sj.astin.multimanager.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.os.Build;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Button;
import android.widget.PopupWindow;
import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.Color;
import android.util.TypedValue;

import com.sj.astin.multimanager.R;
import com.sj.astin.multimanager.ui.FloatingButton;
import com.sj.astin.multimanager.ui.FloatingWindow;
import com.sj.astin.multimanager.ui.FlatButton;
import com.sj.astin.multimanager.ui.MenuItem;
import com.sj.astin.multimanager.ui.MyColor;
import com.sj.astin.multimanager.Utils;
import com.sj.astin.multimanager.TAG;


public class AlwaysOnTopService extends Service implements OnTouchListener {

    private View FloatingView;
    private Button overlayedButton;
    private float offsetX;
    private float offsetY;
    private int originalXPos;
    private int originalYPos;
    private int soft_key_height = 0;
    private boolean moving;
    private boolean showing;
    private WindowManager wm;
    private Context ctx;
    private int WIDTH;
    private int HEIGHT;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        ctx = this;
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
       
        overlayedButton = new FloatingButton(ctx)
                        .setColor(MyColor.BLUE.getColor())
                        .setDrawable((BitmapDrawable) getResources().getDrawable(R.drawable.ic_list_white_48dp))
                        .setStroke(Utils.dip2px(ctx, 3) , MyColor.BLUE.getColorAccent())
                        .setPadding(Utils.dip2px(ctx, 13), Utils.dip2px(ctx, 10), Utils.dip2px(ctx, 13), Utils.dip2px(ctx, 10))
                        .show();
        overlayedButton.setOnTouchListener(this);
        
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(Utils.dip2px(ctx, 50), Utils.dip2px(ctx, 50), WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;
        wm.addView(overlayedButton, params);

        FloatingView = new View(this);
        WindowManager.LayoutParams FloatingViewParams = new WindowManager.LayoutParams(Utils.dip2px(ctx, 50), Utils.dip2px(ctx, 50), WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
        FloatingViewParams.gravity = Gravity.LEFT | Gravity.TOP;
        FloatingViewParams.x = 0;
        FloatingViewParams.y = 0;
        FloatingViewParams.width = 0;
        FloatingViewParams.height = 0;
        wm.addView(FloatingView, FloatingViewParams);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (overlayedButton != null) {
            wm.removeView(overlayedButton);
            wm.removeView(FloatingView);
            overlayedButton = null;
            FloatingView = null;
        }
        Utils.saveData(ctx, TAG.fButton, false);
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        
        this.wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        this.WIDTH = wm.getDefaultDisplay().getWidth();
        this.HEIGHT = wm.getDefaultDisplay().getHeight();
        
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getRawX();
            float y = event.getRawY();

            this.moving = false;

            int[] location = new int[2];
            this.overlayedButton.getLocationOnScreen(location);

            this.originalXPos = location[0];
            this.originalYPos = location[1];

            this.offsetX = originalXPos - x;
            this.offsetY = originalYPos - y;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE && !showing) {
            int[] topLeftLocationOnScreen = new int[2];
            this.FloatingView.getLocationOnScreen(topLeftLocationOnScreen);

            float x = event.getRawX();
            float y = event.getRawY();

            WindowManager.LayoutParams params = (LayoutParams) overlayedButton.getLayoutParams();

            int newX = (int) (offsetX + x);
            int newY = (int) (offsetY + y);

            if (Math.abs(newX - this.originalXPos) < 10 && Math.abs(newY - this.originalYPos) < 10 && !this.moving) {
                return false;
            }
            
            if(params.y < 0) params.y = 0;
            if(params.y > this.HEIGHT - Utils.dip2px(this.ctx, 64)) params.y = this.HEIGHT - Utils.dip2px(this.ctx, 64);

            params.x = newX - (topLeftLocationOnScreen[0]);
            params.y = newY - (topLeftLocationOnScreen[1]);

            this.wm.updateViewLayout(this.overlayedButton, params);
            this.moving = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (this.moving) {
                WindowManager.LayoutParams params = (LayoutParams) overlayedButton.getLayoutParams();

                if(params.y < 0) params.y = 0;
                if(params.y > this.HEIGHT - Utils.dip2px(this.ctx, 74)) params.y = this.HEIGHT - Utils.dip2px(this.ctx, 74);
                return true;
            }
            if (!this.moving && event.getX() >= 0 && event.getX() <= Utils.dip2px(this.ctx, 50) && event.getY() >= 0 && event.getY() <= Utils.dip2px(this.ctx, 50)) {
                LinearLayout main = new LinearLayout(this.ctx);
                main.setOrientation(1);
                main.setGravity(Gravity.TOP|Gravity.CENTER);
                FlatButton memo = new FlatButton(this.ctx)
                    .setText("Memo 열기")
                    .setColor(Color.rgb(255, 255, 255))
                    .setEffectColor(Color.rgb(245, 245, 245))
                    .setParams(-1, Utils.dip2px(this.ctx, 70))
                    .setShadow(true)
                    .setOnClickListener(new FlatButton.OnClickListener() {
                        public void onClick(View _v) {
                            
                        }
                    })
                    .setTextColor(Color.BLACK);
                main.addView(memo.show());
                FlatButton internet = new FlatButton(this.ctx)
                    .setText("Internet Explore 열기")
                    .setColor(Color.rgb(255, 255, 255))
                    .setEffectColor(Color.rgb(245, 245, 245))
                    .setParams(-1, Utils.dip2px(this.ctx, 70))
                    .setShadow(true)
                    .setOnClickListener(new FlatButton.OnClickListener() {
                        public void onClick(View _v) {

                        }
                    })
                    .setTextColor(Color.BLACK);
                main.addView(internet.show());
                FlatButton youtube = new FlatButton(this.ctx)
                    .setText("Youtube 열기")
                    .setColor(Color.rgb(255, 255, 255))
                    .setEffectColor(Color.rgb(245, 245, 245))
                    .setParams(-1, Utils.dip2px(this.ctx, 70))
                    .setShadow(true)
                    .setOnClickListener(new FlatButton.OnClickListener() {
                        public void onClick(View _v) {

                        }
                    })
                    .setTextColor(Color.BLACK);
                main.addView(youtube.show());

                LinearLayout menuLayout = new LinearLayout(this.ctx);
                menuLayout.setOrientation(1);
                menuLayout.addView(new MenuItem(this.ctx).setText("앱 설정").show());
                menuLayout.addView(new MenuItem(this.ctx).setText("투명도 설정").show());
                menuLayout.addView(new MenuItem(this.ctx).setText("작업창 열기").show());
                
                Resources resources = this.getResources();
                int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    soft_key_height = resources.getDimensionPixelSize(resourceId);
                }
                
                FloatingWindow window = new FloatingWindow(this.ctx);
                window.setWidth(this.WIDTH);
                window.setHeight(this.HEIGHT - soft_key_height + Utils.dip2px(this.ctx, 18));
                window.setTitle("Floating Menu");
                window.setResizeable(true);
                window.setDragable(true);
                window.setContentView(main);
                window.setMenuView(menuLayout);
                window.showAtLocation(this.FloatingView, 0, 0);
                
                return true;
            }
        }
        
        return false;
    }
}
