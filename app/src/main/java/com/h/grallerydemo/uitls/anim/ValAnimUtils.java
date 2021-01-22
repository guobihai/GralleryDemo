package com.h.grallerydemo.uitls.anim;

import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
/**
 * @author guobihai
 * 创建日期：2020/6/8
 * desc：属性动画
 *
 */
public class ValAnimUtils {
    private static void startValAnim(int from, int to, ValueAnimator.AnimatorUpdateListener listener,
                                     long duration) {
        ValueAnimator animator = ValueAnimator.ofInt(from, to);
        //设置动画时长
        animator.setDuration(duration);
        //设置插值器，当然你可以不用
        animator.setInterpolator(new DecelerateInterpolator());
        //回调监听
        animator.addUpdateListener(listener);
        //启动动画
        animator.start();
    }

    public static <V extends View> void startZoomAnim(@NonNull V v, int to) {
        startValAnim(v.getWidth(), to, animation -> {
            /* 主要还是通过获取布局参数设置宽高 */
            ViewGroup.LayoutParams lp = v.getLayoutParams();
            //获取改变时的值
            /* 因为我的需求是一个正方形，如果不规则可以等比例设置，这里我就不写了 */
            lp.width = Integer.parseInt(animation.getAnimatedValue().toString());
//            lp.height = size;
            v.setLayoutParams(lp);
        }, 300);
    }
}
