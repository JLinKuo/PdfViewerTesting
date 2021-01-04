package com.github.barteksc.pdfviewer.sign;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;

public class WatermarkArea {
    private String tag = "";
    private int mWatermarkRes = -1;
    private float zoom = 1f;
    private RectF mWatermarkDestRect = null;
    private Paint bitmapPaint = null;
    private Paint outlinePaint = null;

    private ZoomBall zoomBall = new ZoomBall();
    private DelBall delBall = new DelBall();

    public WatermarkArea(String tag, int watermarkRes) {
        this.tag = tag;
        this.mWatermarkRes = watermarkRes;
    }

    public String getTag() { return tag; }
    public float getLeft() { return mWatermarkDestRect.left; }
    public float getTop() { return mWatermarkDestRect.top; }
    public float getRight() { return mWatermarkDestRect.right; }
    public float getBottom() { return mWatermarkDestRect.bottom; }
    public float getZoom() { return zoom; }
    public int getWatermarkRes() { return mWatermarkRes; }
    public RectF getWatermarkDestRect() { return mWatermarkDestRect; }

    public ZoomBall getZoomBall() { return zoomBall; }
    public DelBall getDelBall() { return delBall; }

    public Paint getBitmapPaint() {
        if(bitmapPaint == null) {
            bitmapPaint = new Paint();
            bitmapPaint.setStyle(Paint.Style.STROKE);
            bitmapPaint.setAntiAlias(true);
            int alphaRed = Color.argb(127, 0, 0, 0);
            bitmapPaint.setColor(alphaRed);
        }

        return bitmapPaint;
    }
    public Paint getOutlinePaint() {
        if(outlinePaint == null) {
            outlinePaint = new Paint();
            outlinePaint.setStyle(Paint.Style.STROKE);
            outlinePaint.setStrokeWidth(4);
            outlinePaint.setAntiAlias(true);
            outlinePaint.setColor(Color.RED);
            outlinePaint.setPathEffect(new DashPathEffect(new float[] {16, 16}, 0));
        }

        return outlinePaint;
    }

    public void setZoom(float zoom) { this.zoom = zoom; }
    public void setWatermarkDestRect(RectF watermarkDestRect) {
        this.mWatermarkDestRect = watermarkDestRect;
    }

    public class ZoomBall extends FunctionBall {}
    public class DelBall extends FunctionBall {}
}

