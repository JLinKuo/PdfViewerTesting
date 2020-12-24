package com.github.barteksc.pdfviewer.sign;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class SignArea {
    private String DEFAULT_DATE_FORMAT = "yyyy/mm/dd";
    private int left = -1;
    private int top = -1;
    private int right = -1;
    private int bottom = -1;
    private String email = "";
    private String date = DEFAULT_DATE_FORMAT;

    private Rect emailRect = new Rect();
    private Rect dateRect = new Rect();

    private Paint bgPaint = null;
    private Paint outlinePaint = null;

    private ZoomBall zoomBall = new ZoomBall();
    private AddBall addBall = new AddBall();
    private DelBall delBall = new DelBall();

    public SignArea(String email, int left, int top, int right, int bottom) {
        this.email = email;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
    public int getLeft() { return left; }
    public int getTop() { return top; }
    public int getRight() { return right; }
    public int getBottom() { return bottom; }
    public int getWidth() { return right - left; }
    public int getHeight() { return bottom - top; }
    public String getEmail() { return email; }
    public String getDate() { return date; }

    public SignArea setLeft(int left) {
        this.left = left;
        return this;
    }
    public SignArea setTop(int top) {
        this.top = top;
        return this;
    }
    public SignArea setRight(int right) {
        this.right = right;
        return this;
    }
    public SignArea setBottom(int bottom) {
        this.bottom = bottom;
        return this;
    }

    public Rect getEmailRect() { return emailRect; }
    public Rect getDateRect() { return dateRect; }

    public ZoomBall getZoomBall() { return zoomBall; }
    public AddBall getAddBall() { return addBall; }
    public DelBall getDelBall() { return delBall; }

    public Paint getBackGroundPaint() {
        if(bgPaint == null) {
            bgPaint = new Paint();
            bgPaint.setStyle(Paint.Style.FILL);
            int alphaRed = Color.argb(127, 255, 0, 0);
            bgPaint.setColor(alphaRed);
        }
        return bgPaint;
    }

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

    public class ZoomBall extends FunctionBall {}
    public class AddBall extends FunctionBall {}
    public class DelBall extends FunctionBall {}
}
