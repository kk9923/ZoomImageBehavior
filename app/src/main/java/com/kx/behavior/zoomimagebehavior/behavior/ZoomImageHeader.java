package com.kx.behavior.zoomimagebehavior.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by admin on 2018/11/1.
 */
public class ZoomImageHeader  extends LinearLayout{
    private float mTouchSlop;
    private int firstDownY ;

    private ImageView zoomImage;
    private ViewDragHelper mViewDragHelper;
    private int mMaxScrollHeight;

    public ZoomImageHeader(Context context) {
        this(context,null);
    }

    public ZoomImageHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZoomImageHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mViewDragHelper = ViewDragHelper.create(this, 1f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return zoomImage == child ;
            }

            /**
             *  控制竖直方向最大的移动距离
             */
            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                if (top <0){
                    top = 0 ;
                }
                if (mMaxScrollHeight==0){
                    mMaxScrollHeight =getHeight() - zoomImage.getHeight();
                }
                if (top > mMaxScrollHeight){
                    top = mMaxScrollHeight;
                }
                return top;
            }

            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                System.out.println(top);
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                int  top= releasedChild.getTop();
                if (top <= mMaxScrollHeight /2 ){
                    mViewDragHelper.smoothSlideViewTo(releasedChild,0,0);
                }else {
                    mViewDragHelper.smoothSlideViewTo(releasedChild,0,mMaxScrollHeight);
                }
                postInvalidate();
            }

        });
    }

    @Override
    public void computeScroll() {
        //固定写法
        if (mViewDragHelper.continueSettling(true)) {
            postInvalidate();//注意此处.
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
            // 通过mDragHelper.processTouchEvent(event)来处理事件
            mViewDragHelper.processTouchEvent(event);
            return true; // 返回 true，表示事件被处理了。
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                return true;
//            case MotionEvent.ACTION_MOVE:
//
//                float moveY = event.getY() - firstDownY;
//                //System.out.println(moveY);
//                float currentY = getY();
//              //  setTranslationY(moveY);
//              //  System.out.println(currentY);
//                System.out.println(currentY + moveY);
//                //向上滑动viewpager整体移动
//                if (currentY + moveY <= 0 && currentY + moveY >= - 200) {
//                    //doPagerUp(moveY, currentY);
//                    setTranslationY(currentY + moveY);
//                }
//                //向下移动
//                if (currentY + moveY > 0 && currentY + moveY < 800) {
//                    setTranslationY((currentY + moveY) / 4);
//                    return true;
//                }
//
//                break;
//        }
//        return super.onTouchEvent(event);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
       return mViewDragHelper.shouldInterceptTouchEvent(ev);
//        int action = ev.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                firstDownY = (int) ev.getY();
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int moveY = (int) ev.getY();
//                if (Math.abs(moveY - firstDownY) > mTouchSlop) {
//
//                    return true;
//                }
//        }
//        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        zoomImage = (ImageView) getChildAt(1);
        zoomImage.setScaleX(0.8f);
        zoomImage.setScaleY(0.8f);
    }
}
