package com.hsun.appupdater;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

    private static final int MAX_HEIGHT = 300;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getChildAt(0);
        child.measure(widthMeasureSpec, heightMeasureSpec);
        int width = child.getMeasuredWidth();
        int height = Math.min(child.getMeasuredHeight(), MAX_HEIGHT);
        setMeasuredDimension(width, height);
    }
}
