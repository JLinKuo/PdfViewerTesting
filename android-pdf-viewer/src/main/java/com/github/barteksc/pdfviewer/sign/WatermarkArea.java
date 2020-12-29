package com.github.barteksc.pdfviewer.sign;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class WatermarkArea {
    private int mWatermarkRes = -1;
    private RectF mWatermarkDestRect = null;
    private Paint outlinePaint = null;

    public WatermarkArea(int watermarkRes) { this.mWatermarkRes = watermarkRes; }

    public int getWatermarkRes() { return mWatermarkRes; }
    public RectF getWatermarkDestRect() { return mWatermarkDestRect; }
    public Paint getOutlinePaint() {
        if(outlinePaint == null) {
            outlinePaint = new Paint();
            outlinePaint.setStyle(Paint.Style.STROKE);
            outlinePaint.setStrokeWidth(8);
            outlinePaint.setAntiAlias(true);
            outlinePaint.setColor(Color.RED);
        }

        return outlinePaint;
    }

    public void setWatermarkDestRect(RectF watermarkDestRect) {
        this.mWatermarkDestRect = watermarkDestRect;
    }
}

