package com.example.musicplayer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class SwipeLayout extends LinearLayout {
    private static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                upX = event.getX();
                upY = event.getY();
                float deltaX = upX - downX;
                float deltaY = upY - downY;

                // Horizontal swipe
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                        // Left to right swipe
                        if (deltaX > 0) {
                            performSwipeAction();
                            return true;
                        }
                    }
                }
                break;
        }
        return false;
    }

    private void performSwipeAction() {
        // Implement the swipe action here
        // For example, you can delete the song or perform any other action
        if (getChildCount() > 1) {
            View deleteView = getChildAt(1);
            deleteView.setVisibility(VISIBLE);
        }
    }
}

