package com.sj.astin.multimanager.ui;

import android.view.View;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import com.sj.astin.multimanager.ui.FlatButton;
import com.sj.astin.multimanager.Utils;
import android.view.View.*;


public class drawable {/*
	public static void RippleDrawable(Context ctx, final View view, final float width, final float height, final float x, final float y, final int color, final int _color, final FlatButton.OnClickListener listener, Drawable drawable) {
		
		final float radius = Math.min(width, height) / 2;
		final float max_radius = (float) (Math.hypot(width, height) / 2) + Utils.dip2px(ctx, 100);
		
		final ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[] {radius, max_radius}),
		_valueAnimatorX = ValueAnimator.ofFloat(new float[] {x, width / 2}),
		_valueAnimatorY = ValueAnimator.ofFloat(new float[] {y, height / 2});
		
		_valueAnimatorX.setDuration(400);
		_valueAnimatorY.setDuration(400);
		
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			public boolean click = false;
			public void onAnimationUpdate(ValueAnimator _valueAnimator) {
				float current_radius = _valueAnimator.getAnimatedValue(),
				circle_point_x = _valueAnimatorX.getAnimatedValue(),
				circle_point_y = _valueAnimatorY.getAnimatedValue();
				
				int percent = (int) (255 * (1 - (current_radius / max_radius)));
				
				if(current_radius < max_radius) {
					drawCircle(view, (int) width, (int) height, circle_point_x, circle_point_y, color,  _color, current_radius, percent);
				}
				
				if(circle_point_x == width / 2 && !click) {
					view.setBackgroundDrawable(new ColorDrawable(color));
					listener.onClick(view);
					
					click = true;
				}
				
				if(circle_point_x >= width / 2 && click) {
					view.setBackgroundDrawable(new ColorDrawable(color));
				}
			}
		});
			
		valueAnimator.setDuration(450);
		valueAnimator.start();
		_valueAnimatorX.start();
		_valueAnimatorY.start();
	}
	
	public static void drawCircle(View view, int width, int height, float x, float y, int color, int _color, float radius, int alpha, Drawable drawable) {
		Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bm);
		final Paint paint = new Paint();
		
		paint.setColor(_color);
		paint.setAlpha(alpha);
		paint.setAntiAlias(true);
		
		canvas.drawCircle(x, y, radius, paint);
		
		view.setBackgroundDrawable(new LayerDrawable(new Drawable[] {new ColorDrawable(color), new BitmapDrawable(bm)}));
	}*/
}
