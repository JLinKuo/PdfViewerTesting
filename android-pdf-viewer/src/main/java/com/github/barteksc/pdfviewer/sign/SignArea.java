package com.github.barteksc.pdfviewer.sign;

public class SignArea {
    private int left = -1;
    private int top = -1;
    private int right = -1;
    private int bottom = -1;

    public SignArea(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
    public int getLeft() { return left; }
    public int getTop() { return top; }
    public int getRight() { return right; }
    public int getBottom() { return bottom; }
}
