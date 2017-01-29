package com.sj.astin.multimanager.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ColorDrawable;
import android.widget.LinearLayout;
import android.view.View;
import android.view.Gravity;
import android.view.MotionEvent;
import android.animation.ValueAnimator;

import com.sj.astin.multimanager.ui.drawable;
import com.sj.astin.multimanager.ui.ShadowDrawable;
import com.sj.astin.multimanager.Utils;


public class FlatButton {
	
	public interface OnClickListener {
		public void onClick(View view)
	}
	
	private Context CONTEX;
	private android.widget.Button btn;
	private String text = "";
	private int width = 0;
	private int height = 0;
	private int color = Color.argb(0, 0, 0, 0);
	private int effectColor = Color.rgb(0, 150, 255);
	private FlatButton.OnClickListener listener;
    private Drawable drawable;
    private int[] padding = new int[] {0, 0, 0, 0};
    private boolean shadow = false;
    private LayerDrawable layerDrawable;
    private LayerDrawable _layerDrawable;
	
	public FlatButton(Context ctx) {
		this.btn = new android.widget.Button(ctx);
		this.CONTEX = ctx;
	}
	
	public FlatButton setText(String text) {
		this.text = text;
		this.btn.setText(text);
		
		return this;
	}
	
	public FlatButton setTextColor(int color) {
		this.btn.setTextColor(color);
		
		return this;
	}
	
	public FlatButton setTextSize(int size) {
		this.btn.setTextSize(size);
		
		return this;
	}
    
	public FlatButton setColor(int color) {
		this.color = color;
		
		return this;
	}
    
    public FlatButton setEffectColor(int eColor) {
        this.effectColor = eColor;
        return this;
    }
	
	public FlatButton setParams(int _width, int _height) {
		this.width = _width;
		this.height = _height;
		this.btn.setLayoutParams(new LinearLayout.LayoutParams(width, height));
		
		return this;
	}
    
    public FlatButton setShadow(boolean _shadow) {
        this.shadow = _shadow;
        return this;
    }
	
	public FlatButton setOnClickListener(FlatButton.OnClickListener _listener) {
		this.listener = _listener;
		
		return this;
	}
    
    public FlatButton setBackgroundDrawable(Drawable _drawable) {
        
        final GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(this.color);
        LayerDrawable __layerDrawable = new LayerDrawable(new Drawable[] { gradientDrawable, this.drawable });
        this.drawable = __layerDrawable;
        this.btn.setBackgroundDrawable(__layerDrawable);
        return this;
    }
    
    public FlatButton setBackgroundDrawable(Drawable _drawable, int l, int t, int r, int b) {
        this.drawable = _drawable;
        this.padding = new int[] { l, t, r, b };
        
        final GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(this.color);
        LayerDrawable __layerDrawable = new LayerDrawable(new Drawable[] { gradientDrawable, this.drawable });
        __layerDrawable.setLayerInset(1, this.padding[0], this.padding[1], this.padding[2], this.padding[3]);
        this.drawable = __layerDrawable;
        this.btn.setBackgroundDrawable(__layerDrawable);
        return this;
    }
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public android.widget.Button show() {
        final GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(this.color);
        final GradientDrawable _gradientDrawable = new GradientDrawable();
        _gradientDrawable.setColor(this.effectColor);
        
        if(this.drawable != null) {
            layerDrawable = new LayerDrawable(new Drawable[] { gradientDrawable, this.drawable });
            layerDrawable.setLayerInset(1, this.padding[0], this.padding[1], this.padding[2], this.padding[3]);
            _layerDrawable = new LayerDrawable(new Drawable[] { _gradientDrawable, this.drawable });
            _layerDrawable.setLayerInset(1, this.padding[0], this.padding[1], this.padding[2], this.padding[3]);
            
            if(!this.shadow) this.btn.setBackgroundDrawable(layerDrawable);
            if(this.shadow) this.btn.setBackgroundDrawable(new ShadowDrawable(this.CONTEX).setDrawable(layerDrawable).get());
        }
        if(this.drawable == null) {
            if(!this.shadow) this.btn.setBackgroundColor(this.color);
            if(this.shadow) this.btn.setBackgroundDrawable((new ShadowDrawable(this.CONTEX).setDrawable(new ColorDrawable(this.color)).get()));
        }
        
		this.btn.setAllCaps(false);
		this.btn.setGravity(Gravity.CENTER);
		final FlatButton that = this;
        
		this.btn.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				switch(event.getAction()) {
					case MotionEvent.ACTION_DOWN:
                        if(that.drawable != null) {
                            if(!that.shadow) that.btn.setBackgroundDrawable(_layerDrawable);
                            if(that.shadow) that.btn.setBackgroundDrawable(new ShadowDrawable(that.CONTEX).setDrawable(_layerDrawable).get());
                        }
                        if(that.drawable == null) {
                            if(!that.shadow) that.btn.setBackgroundColor(that.effectColor);
                            if(that.shadow) that.btn.setBackgroundDrawable((new ShadowDrawable(that.CONTEX).setDrawable(new ColorDrawable(that.effectColor)).get()));
                        }
                        
						break;
						
					case MotionEvent.ACTION_MOVE:
						if(event.getX() < 0 || event.getY() < 0 || event.getX() > view.getWidth() || event.getY() > view.getHeight()) {
                            if(that.drawable != null) {
                                if(!that.shadow) that.btn.setBackgroundDrawable(layerDrawable);
                                if(that.shadow) that.btn.setBackgroundDrawable(new ShadowDrawable(that.CONTEX).setDrawable(layerDrawable).get());
                            }
                            if(that.drawable == null) {
                                if(!that.shadow) that.btn.setBackgroundColor(that.color);
                                if(that.shadow) that.btn.setBackgroundDrawable((new ShadowDrawable(that.CONTEX).setDrawable(new ColorDrawable(that.color)).get()));
                            }
                        }
                        break;
						
					case MotionEvent.ACTION_UP:
						if(event.getX() > 0 && event.getY() > 0 && event.getX() < view.getWidth() && event.getY() < view.getHeight()) {
                            if(that.listener != null) that.listener.onClick(view);
                        }
                        if(that.drawable != null) {
                            if(!that.shadow) that.btn.setBackgroundDrawable(layerDrawable);
                            if(that.shadow) that.btn.setBackgroundDrawable(new ShadowDrawable(that.CONTEX).setDrawable(layerDrawable).get());
                        }
                        if(that.drawable == null) {
                            if(!that.shadow) that.btn.setBackgroundColor(that.color);
                            if(that.shadow) that.btn.setBackgroundDrawable((new ShadowDrawable(that.CONTEX).setDrawable(new ColorDrawable(that.color)).get()));
                        }
                        break;
						
					case MotionEvent.ACTION_CANCEL:
						if(that.drawable != null) {
                            if(!that.shadow) that.btn.setBackgroundDrawable(layerDrawable);
                            if(that.shadow) that.btn.setBackgroundDrawable(new ShadowDrawable(that.CONTEX).setDrawable(layerDrawable).get());
                        }
                        if(that.drawable == null) {
                            if(!that.shadow) that.btn.setBackgroundColor(that.color);
                            if(that.shadow) that.btn.setBackgroundDrawable((new ShadowDrawable(that.CONTEX).setDrawable(new ColorDrawable(that.color)).get()));
                        }
						break;
				}
				
				return true;
			}
		});
		
		return this.btn;
	}
}
