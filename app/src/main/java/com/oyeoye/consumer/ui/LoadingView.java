package com.oyeoye.consumer.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.oyeoye.consumer.R;

/**
 * @author Lukasz Piliszczuk - lukasz.pili@gmail.com
 */
public class LoadingView extends FrameLayout {

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setBackgroundColor(getResources().getColor(R.color.black_opacity_40));

        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setIndeterminate(true);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        addView(progressBar, params);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
