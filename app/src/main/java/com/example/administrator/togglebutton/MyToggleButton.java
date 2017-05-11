package com.example.administrator.togglebutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/5/11.
 */

public class MyToggleButton extends View implements View.OnClickListener{
    private Bitmap backgroundBitmap;
    private Paint paint;
    private Bitmap slideBitmap;
    private float slideLeft;
    private int slideLeftMax;
    private boolean curState = false;
    private boolean clickEnable = true;
    private float lastX;
    private float startX;

    public MyToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }



    public MyToggleButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyToggleButton(Context context) {
        this(context,null);
    }

    private void initView() {
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        slideBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
        slideLeftMax = backgroundBitmap.getWidth() -slideBitmap.getWidth();
        paint = new Paint();
        paint.setAntiAlias(true);
        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
        //super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
        canvas.drawBitmap(slideBitmap, slideLeft, 0, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float newX = event.getX();
                float distanceX = newX - startX;
                flushView(distanceX);
                startX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    private void flushView(float distanceX){
        slideLeft  +=  distanceX;
        invalidate();
    }

    @Override
    public void onClick(View v) {

            curState = !curState;
            flushState();

    }

    private void flushState() {
        if(curState){
            slideLeft = slideLeftMax ;
        }else{
            slideLeft  = 0;
        }

        invalidate();
    }
}
