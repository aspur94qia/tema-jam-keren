package com.jam.theme;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import java.util.Calendar;
public class AnalogClockView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public AnalogClockView(Context c, AttributeSet a){ super(c,a); }
    @Override protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int w=getWidth(), h=getHeight(), r=Math.min(w,h)/2 - 20;
        int cx=w/2, cy=h/2;
        paint.setStyle(Paint.Style.STROKE); paint.setStrokeWidth(6); paint.setColor(Color.parseColor("#00FFD1"));
        canvas.drawCircle(cx,cy,r,paint);
        Calendar cal=Calendar.getInstance();
        float hour=cal.get(Calendar.HOUR)%12 + cal.get(Calendar.MINUTE)/60f;
        float minute=cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND)/60f;
        float second=cal.get(Calendar.SECOND);
        drawHand(canvas,cx,cy,hour/12*360, r*0.5f, 10, Color.WHITE);
        drawHand(canvas,cx,cy,minute/60*360, r*0.75f, 6, Color.WHITE);
        drawHand(canvas,cx,cy,second/60*360, r*0.85f, 3, Color.parseColor("#00FFD1"));
        paint.setStyle(Paint.Style.FILL); paint.setColor(Color.parseColor("#00FFD1"));
        canvas.drawCircle(cx,cy,12,paint);
        postInvalidateDelayed(1000);
    }
    void drawHand(Canvas c,int x,int y,float angle,float len,float stroke,int color){
        paint.setColor(color); paint.setStrokeWidth(stroke);
        double rad=Math.toRadians(angle-90);
        float ex=x+(float)(len*Math.cos(rad)), ey=y+(float)(len*Math.sin(rad));
        c.drawLine(x,y,ex,ey,paint);
    }
}
