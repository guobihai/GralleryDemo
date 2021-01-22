package com.h.grallerydemo.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

public class ViewSwitcher extends ViewAnimator {
    /**
     * 用来创建两个子View的工厂对象，
     */
    ViewFactory mFactory;
    int mWhichChild = 0;
    boolean mFirstTime;

    public ViewSwitcher(Context context) {
        super(context);
    }

    public ViewSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 该方法用来添加View，可以看到如果ViewSwitcher中的子View大于等于两个的时候
     * 再添加View的时候会报错，
     */
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() >= 2) {
            throw new IllegalStateException("Can't add more than 2 views to a ViewSwitcher");
        }
        super.addView(child, index, params);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ViewSwitcher.class.getName();
    }

    /**
     * 获取到下一个view
     */
    public View getNextView() {
        int which = mWhichChild == 0 ? 1 : 0;
        mWhichChild = which == 1 ? 0 : 1;
        return getChildAt(which);
    }

    /**
     * 该方法通过ViewFactory工厂，创建View，添加到VeiwSwitcher中，并返回该view
     */
    private View obtainView() {
        View child = mFactory.makeView();
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        if (lp == null) {
            lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
        addView(child, lp);
        return child;
    }

    /**
     * 设置一个ViewFactory工厂对象，调用obtainView方法，在obtainView中调用ViewFactory的makeView方法创建View，并添加到ViewSwitcher中，
     */
    public void setFactory(ViewFactory factory) {
        mFactory = factory;
//        obtainView();
        obtainView();
    }

    /**
     * 该方法用来重置ViewSwitcher中子View为不可见
     */
    public void reset() {
        mFirstTime = true;
        View v;
        v = getChildAt(0);
        if (v != null) {
            v.setVisibility(View.GONE);
        }
        v = getChildAt(1);
        if (v != null) {
            v.setVisibility(View.GONE);
        }
    }

    /**
     * 用来创建View添加到ViewSwitcher中的ViewFactory接口
     */
    public interface ViewFactory {
        View makeView();
    }
}
