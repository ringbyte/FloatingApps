package com.sj.astin.multimanager.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.LayerDrawable;

import com.sj.astin.multimanager.Utils;


public class ShadowDrawable {
    NinePatchDrawable shadowDrawable;
    LayerDrawable layerDrawable;
    Context ctx;
    
    public ShadowDrawable(Context _ctx) {
        this.shadowDrawable = (NinePatchDrawable) _ctx.getDrawable(android.R.drawable.dialog_frame);
        this.ctx = _ctx;
    }
    
    public ShadowDrawable setDrawable(Drawable drawable) {
        this.layerDrawable = new LayerDrawable(new Drawable[] { this.shadowDrawable, drawable });
        this.layerDrawable.setLayerInset(0, Utils.dip2px(this.ctx, 5), Utils.dip2px(this.ctx, 5), Utils.dip2px(this.ctx, 5), Utils.dip2px(this.ctx, 5));
        return this;
    }
    
    public ShadowDrawable setPadding(int l, int t, int r, int b) {
        this.layerDrawable.setLayerInset(0, l, t, r, b);
        return this;
    }
    
    public LayerDrawable get() {
        return this.layerDrawable;
    }
}
