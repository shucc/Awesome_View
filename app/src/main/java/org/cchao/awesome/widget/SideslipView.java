package org.cchao.awesome.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by chenchao on 16/3/1.
 */
public class SideslipView extends LinearLayout {

    private final float DAMPING = 10.0f;

    private ViewDragHelper mDragView;

    private View mMainView;
    private View mSideView;
    //侧滑菜单宽度
    private int mSideWidth;
    //主菜单宽度
    private int mMainWidth;
    //侧滑菜单全显示时距左边边距
    private int mSideLeft;

    private Status status = Status.Close;
    private Status preStatus = Status.Close; // 前一次保持的状态

    private OnStatusListener listener;

    public enum Status {
        Open, Close
    }

    public interface OnStatusListener {
        void statusChanged(Status status);
    }

    public void setListener(OnStatusListener listener) {
        this.listener = listener;
    }

    public SideslipView(Context context) {
        this(context, null);
    }

    public SideslipView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SideslipView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        mDragView = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (child == mMainView) {
                    if (left > 0) {
                        return 0;
                    } else if (-left > mSideWidth) {
                        return -mSideWidth;
                    } else {
                        return left;
                    }
                } else if (child == mSideView) {
                    if (left < mSideLeft) {
                        return mSideLeft;
                    }
                }
                return left;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return mSideWidth;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                if (changedView == mMainView) {
                    mSideView.offsetLeftAndRight(dx);
                } else {
                    mMainView.offsetLeftAndRight(dx);
                }
                invalidate();
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (releasedChild == mMainView) {
                    if (status == Status.Close && -mMainView.getLeft() > mSideWidth / DAMPING) {
                        open();
                    } else if (status == Status.Open && -mMainView.getLeft() < mSideWidth * (DAMPING - 1) / DAMPING){
                        close();
                    } else if (status == Status.Close) {
                        close();
                    } else {
                        open();
                    }
                } else if (releasedChild == mSideView) {
                    if (xvel >= 0 && mSideView.getLeft() > mSideLeft + mSideWidth / DAMPING) {
                        close();
                    } else {
                        open();
                    }
                }
            }
        });
    }


    public void close() {
        if (mDragView.smoothSlideViewTo(mMainView, 0, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        preStatus = status;
        status = Status.Close;
        if (listener != null && preStatus == Status.Open) {
            listener.statusChanged(status);
        }
    }

    public void open() {
        if (mDragView.smoothSlideViewTo(mMainView, -mSideWidth, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        preStatus = status;
        status = Status.Open;
        if (listener != null && preStatus == Status.Close) {
            listener.statusChanged(status);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // 开始执行动画
        if (mDragView.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragView.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragView.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount()!=2){
            throw new IllegalArgumentException("子view的数量必须为2个");
        }
        mMainView = getChildAt(0);
        mSideView = getChildAt(1);
        int width = getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams params = mMainView.getLayoutParams();
        params.width = width;
        mMainView.setLayoutParams(params);
        mMainWidth = mMainView.getLayoutParams().width;
        mSideWidth = mSideView.getLayoutParams().width;
        mSideLeft = mMainWidth - mSideWidth;
    }
}
