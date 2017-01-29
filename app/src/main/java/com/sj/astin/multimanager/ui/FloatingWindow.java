package com.sj.astin.multimanager.ui;

import android.content.Context;
import android.widget.PopupWindow;
import android.widget.FrameLayout;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.util.TypedValue;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;
import android.content.res.Resources;
import java.lang.Runnable;
import android.os.Handler;

import com.sj.astin.multimanager.ui.FlatButton;
import com.sj.astin.multimanager.ui.MyColor;
import com.sj.astin.multimanager.ui.Divider;
import com.sj.astin.multimanager.ui.PopupMenu;
import com.sj.astin.multimanager.ui.MenuItem;
import com.sj.astin.multimanager.Utils;
import com.sj.astin.multimanager.R;


public class FloatingWindow {
    private Context ctx;
    private String title = "";
    private int width, height, WIDTH, HEIGHT;
    private int x, y;
    private float _x, _y;
    private int dX = 0, dY = 0;
    private Bitmap icon;
    private FloatingWindow.onDismissListener listener;
    private float offsetX, offsetY;
    private int originalXPos, originalYPos;
    private boolean moving, _moving, resize, drag;
    private int soft_key_height;
    
    private LinearLayout main_layout;
    private LinearLayout topBarLayout;
    private TextView titleView;
    private FlatButton iconView;
    private PopupWindow window;
    private View contentView;
    private Button resizeButton;
    private LinearLayout contentViewLayout;
    private LinearLayout bottom_menu_layout;
    private View menuView;
    private WindowManager wm;
    
    public interface onDismissListener {
        public void onWindowDismiss(FloatingWindow window);
    }
    
    public FloatingWindow(Context _ctx) {
        this.ctx = _ctx;
        this.wm = (WindowManager) this.ctx.getSystemService(Context.WINDOW_SERVICE);
        this.WIDTH = this.wm.getDefaultDisplay().getWidth();
        this.HEIGHT = this.wm.getDefaultDisplay().getHeight();
        this.window = new PopupWindow();
        this.iconView = new FlatButton(this.ctx);
        this.titleView = new TextView(this.ctx);
        this.iconView.setBackgroundDrawable(Utils.ColorFilter(((BitmapDrawable) this.ctx.getResources().getDrawable(R.drawable.ic_window_restore)), Color.BLACK), Utils.dip2px(this.ctx, 3), Utils.dip2px(this.ctx, 3), Utils.dip2px(this.ctx, 3), Utils.dip2px(this.ctx, 3));
        
        Resources resources = this.ctx.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            this.soft_key_height = resources.getDimensionPixelSize(resourceId) + Utils.dip2px(this.ctx, 18);
        }
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public void setWidth(int _width) {
        this.width = _width;
    }
    
    public void setHeight(int _height) {
        this.height = _height;
    }
    
    public void setTitle(String _title) {
        this.title = _title;
        this.titleView.setText(this.title);
    }
    
    public void setResizeable(boolean _resize) {
        this.resize = _resize;
    }
    
    public void setDragable(boolean _drag) {
        this.drag = _drag;
    }
    
    public void setIcon(Bitmap _icon) {
        this.icon = _icon;
        this.iconView.setBackgroundDrawable(new BitmapDrawable(_icon));
    }
    
    public void setOnDismissListener(FloatingWindow.onDismissListener _listener) {
        this.listener = _listener;
    }
    
    public void setContentView(View v) {
        if(this.main_layout == null) {
            this.contentView = v;
        }
        if(this.main_layout != null) {
            this.contentViewLayout.addView(v);
        }
    }
    
    public void setMenuView(View v) {
        this.menuView = v;
    }
    
    public void showAtLocation(final View v, int x, int y) {
        this.topBarLayout = new LinearLayout(this.ctx);
        this.topBarLayout.setOrientation(0);
        this.topBarLayout.setGravity(Gravity.LEFT|Gravity.TOP);
        this.topBarLayout.setLayoutParams(new LinearLayout.LayoutParams(this.width, Utils.dip2px(this.ctx, 31)));
        
        final FloatingWindow that = this;
        final LinearLayout windowLayout = new LinearLayout(this.ctx);
        windowLayout.setOrientation(1);
        windowLayout.addView(new MenuItem(this.ctx)
                            .setText("최소화 하기")
                            .setOnClickListener(new MenuItem.OnClickListener() {
                                public void onClick(View v) {
                                    
                                }
                            })
                            .show());
        windowLayout.addView(new MenuItem(this.ctx)
                             .setText("최대화 하기")
                             .setOnClickListener(new MenuItem.OnClickListener() {
                                     public void onClick(View v) {
                                         
                                     }
                                 })
                             .show());
        windowLayout.addView(new MenuItem(this.ctx)
                             .setText("화면 크기 저장")
                             .setOnClickListener(new MenuItem.OnClickListener() {
                                     public void onClick(View v) {

                                     }
                                 })
                             .show());
        windowLayout.addView(new MenuItem(this.ctx)
                             .setText("화면 위치 저장")
                             .setOnClickListener(new MenuItem.OnClickListener() {
                                     public void onClick(View v) {

                                     }
                                 })
                             .show());
        windowLayout.addView(new MenuItem(this.ctx)
                             .setText("맨 위로")
                             .setOnClickListener(new MenuItem.OnClickListener() {
                                     public void onClick(View v) {

                                     }
                                 })
                             .show());
        windowLayout.addView(new MenuItem(this.ctx)
                             .setText("맨 아래로")
                             .setOnClickListener(new MenuItem.OnClickListener() {
                                     public void onClick(View v) {

                                     }
                                 })
                             .show());
        final PopupMenu menuWindow = new PopupMenu(that.ctx);
        menuWindow.setWidth(Utils.dip2px(that.ctx, 200));
        menuWindow.setHeight(-2);
        menuWindow.setContentView(windowLayout);
        
        this.iconView.setColor(Color.TRANSPARENT);
        this.iconView.setEffectColor(Color.argb(200, 180, 180, 180));
        this.iconView.setParams(Utils.dip2px(this.ctx, 30), Utils.dip2px(this.ctx, 30));
        this.iconView.setOnClickListener(new FlatButton.OnClickListener() {
            public void onClick(View _v) {
                int[] location = new int[2];
                _v.getLocationOnScreen(location);
                menuWindow.showAtLocation(v, location[0], location[1]);
            }
        });
        this.topBarLayout.addView(this.iconView.show());
        
        this.titleView.setTextColor(Color.BLACK);
        this.titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        this.titleView.setBackgroundColor(Color.TRANSPARENT);
        this.titleView.setGravity(Gravity.CENTER);
        this.titleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    float x = event.getRawX();
                    float y = event.getRawY();
                    moving = false;
                    
                    int[] location = new int[2];
                    that.titleView.getLocationOnScreen(location);
                    
                    originalXPos = location[0];
                    originalYPos = location[1];
                    
                    offsetX = originalXPos - x;
                    offsetY = originalYPos - y;
                } else if (event.getAction() == MotionEvent.ACTION_MOVE && that.drag) {
                    int[] topLeftLocationOnScreen = new int[2];
                    v.getLocationOnScreen(topLeftLocationOnScreen);
                    
                    float x = event.getRawX();
                    float y = event.getRawY();
                    
                    int newX = (int) (offsetX + x);
                    int newY = (int) (offsetY + y);
                    
                    if (Math.abs(newX - originalXPos) < 10 && Math.abs(newY - originalYPos) < 10 && !moving) {
                        return false;
                    }
                    that.x = newX - (topLeftLocationOnScreen[0]) - Utils.dip2px(that.ctx, 40);
                    that.y = newY - (topLeftLocationOnScreen[1]) - Utils.dip2px(that.ctx, 10);
                    that.window.update(that.x, that.y, that.window.getWidth(), that.window.getHeight());
                    moving = true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (moving) {
                        return true;
                    }
                }
                return true;
            }
        });
        this.titleView.setLayoutParams(new LinearLayout.LayoutParams(this.width - Utils.dip2px(this.ctx, 90), Utils.dip2px(this.ctx, 30)));
        this.topBarLayout.addView(this.titleView);
        
        final PopupMenu popup_menu = new PopupMenu(that.ctx);
        popup_menu.setWidth(Utils.dip2px(that.ctx, 200));
        popup_menu.setHeight(-2);
        popup_menu.setContentView(that.menuView);
        
        final FlatButton menu = new FlatButton(this.ctx);
        menu.setText("")
            .setColor(Color.TRANSPARENT)
            .setEffectColor(Color.argb(200, 180, 180, 180))
            .setParams(Utils.dip2px(this.ctx, 30), Utils.dip2px(this.ctx, 30))
            .setBackgroundDrawable(Utils.ColorFilter(((BitmapDrawable) this.ctx.getResources().getDrawable(R.drawable.ic_menu_white_48dp)), Color.BLACK), Utils.dip2px(this.ctx, 3), Utils.dip2px(this.ctx, 3), Utils.dip2px(this.ctx, 3), Utils.dip2px(this.ctx, 3))
            .setOnClickListener(new FlatButton.OnClickListener() {
                public void onClick(View _v) {
                    int[] location = new int[2];
                    _v.getLocationOnScreen(location);
                    popup_menu.showAtLocation(v, location[0], location[1]);
                }
            });
        
        this.topBarLayout.addView(menu.show());
        
        Button close = new FlatButton(this.ctx)
            .setText("")
            .setColor(Color.TRANSPARENT)
            .setEffectColor(Color.argb(200, 180, 180, 180))
            .setParams(Utils.dip2px(this.ctx, 30), Utils.dip2px(this.ctx, 30))
            .setBackgroundDrawable(Utils.ColorFilter(((BitmapDrawable) this.ctx.getResources().getDrawable(R.drawable.ic_close_white_48dp)), MyColor.RED.getColor()), Utils.dip2px(this.ctx, 3), Utils.dip2px(this.ctx, 3), Utils.dip2px(this.ctx, 3), Utils.dip2px(this.ctx, 3))
            .setOnClickListener(new FlatButton.OnClickListener() {
                public void onClick(View view) {
                    that.window.dismiss();
                    that.window = null;
                    if(that.listener != null) {
                        that.listener.onWindowDismiss(that);
                    }
                }
            })
            .show();
        
        this.topBarLayout.addView(close);
        
        this.main_layout = new LinearLayout(this.ctx);
        this.main_layout.setOrientation(1);
        this.main_layout.addView(this.topBarLayout);
        this.main_layout.addView(new Divider(this.ctx).setColor(Color.BLACK).setParams(this.width, Utils.dip2px(this.ctx, 3)).show());
        
        this.contentViewLayout = new LinearLayout(this.ctx);
        this.contentViewLayout.setOrientation(1);
        this.contentViewLayout.setGravity(Gravity.TOP|Gravity.CENTER);
        this.contentViewLayout.setLayoutParams(new LinearLayout.LayoutParams(this.width, this.height - Utils.dip2px(this.ctx, 51)));
        this.contentViewLayout.addView(this.contentView);
        this.main_layout.addView(this.contentViewLayout);
        this.main_layout.addView(new Divider(this.ctx).setColor(Color.BLACK).setParams(this.width, Utils.dip2px(this.ctx, 3)).show());
        
        this.bottom_menu_layout = new LinearLayout(this.ctx);
        this.bottom_menu_layout.setOrientation(0);
        this.bottom_menu_layout.setGravity(Gravity.RIGHT|Gravity.CENTER);
        this.resizeButton = new Button(this.ctx);
        this.resizeButton.setBackgroundDrawable(Utils.ColorFilter((BitmapDrawable) this.ctx.getResources().getDrawable(R.drawable.ic_resize_bottom_right), Color.BLACK));
        this.resizeButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, final MotionEvent event) {
                new Handler().postDelayed(new Runnable() {   
                    @Override
                    public void run() {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            _x = event.getRawX();
                            _y = event.getRawY();
                            that.width = that.window.getWidth();
                            that.height = that.window.getHeight();
                            _moving = false;
                        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

                            if(that.resize) {
                                float x = event.getRawX();
                                float y = event.getRawY();

                                dX = (int) _x - (int) x;
                                dY = (int) _y - (int) y;

                                that.window.update(that.width - dX, that.height - dY);
                                that.titleView.setLayoutParams(new LinearLayout.LayoutParams(that.width - Utils.dip2px(that.ctx, 90) - dX, Utils.dip2px(that.ctx, 30)));
                                that.contentViewLayout.setLayoutParams(new LinearLayout.LayoutParams(that.width - dX, that.height - Utils.dip2px(that.ctx, 51) - dY));
                                that.bottom_menu_layout.setLayoutParams(new LinearLayout.LayoutParams(that.width - dX, Utils.dip2px(that.ctx, 15)));
                                
                                _moving = true;
                            }
                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (_moving && that.resize) {
                                that.window.update(that.width - dX, that.height - dY);
                                that.topBarLayout.setLayoutParams(new LinearLayout.LayoutParams(that.width - dX, Utils.dip2px(that.ctx, 30)));
                                that.titleView.setLayoutParams(new LinearLayout.LayoutParams(that.width - Utils.dip2px(that.ctx, 90) - dX, Utils.dip2px(that.ctx, 30)));
                                that.contentViewLayout.setLayoutParams(new LinearLayout.LayoutParams(that.width - dX, that.height - Utils.dip2px(that.ctx, 51) - dY));
                                that.bottom_menu_layout.setLayoutParams(new LinearLayout.LayoutParams(that.width - dX, Utils.dip2px(that.ctx, 15)));
                                that.bottom_menu_layout.setGravity(Gravity.RIGHT|Gravity.CENTER);

                                that.width = that.window.getWidth() + dX;
                                that.height = that.window.getHeight() + dY;
                            }
                        }
                    }
                }, 10);
                return true;
            }
        });
        this.bottom_menu_layout.addView(this.resizeButton, Utils.dip2px(this.ctx, 15), Utils.dip2px(this.ctx, 15));
        this.main_layout.addView(this.bottom_menu_layout);
        
        //LayerDrawable layerDrawable = new LayerDrawable(new Drawable[] { (NinePatchDrawable)this.ctx.getResources().getDrawable(android.R.drawable.dialog_frame), new ColorDrawable(Color.WHITE) });
        //layerDrawable.setLayerInset(0, Utils.dip2px(this.ctx, 5), Utils.dip2px(this.ctx, 5) + 3, Utils.dip2px(this.ctx, 5), Utils.dip2px(this.ctx, 5));
        this.main_layout.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        this.window.setContentView(this.main_layout);
        this.window.setWidth(this.width);
        this.window.setHeight(this.height);
        
        this.window.showAtLocation(v, Gravity.LEFT|Gravity.TOP, x, y);
    }
}
