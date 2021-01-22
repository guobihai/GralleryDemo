package com.h.grallerydemo;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.h.grallerydemo.checkxp.CheckXpUtils;
import com.h.grallerydemo.checkxp.RootCheck;
import com.h.grallerydemo.events.LiveDataBus;
import com.h.grallerydemo.impl.SuperStar;
import com.h.grallerydemo.interfaces.Star;
import com.h.grallerydemo.proxy.SubInvocationHandler;
import com.h.grallerydemo.proxy.entry.MethodParam;
import com.h.grallerydemo.proxy.interfaces.InvokeCallBackInterface;
import com.h.grallerydemo.uitls.ClickToast;
import com.h.grallerydemo.uitls.DensityUtils;
import com.h.grallerydemo.uitls.ToastUtils;
import com.h.grallerydemo.uitls.anim.ValAnimUtils;
import com.h.grallerydemo.uitls.widget.ToastDialog;
import com.h.grallerydemo.views.ViewSwitcher;


/**
 * 动态代理
 */
public class ProxyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);

        ViewFlipper viewFlipper = findViewById(R.id.viewflipper);

        // 设置进入动画
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_in));
        // 设置滚出动画
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_out));


        findViewById(R.id.button).setOnClickListener(v -> {
//            proxy();
//            ClickToast.showToast(ProxyActivity.this,60*1000);
//            ToastUtils.showToast(ProxyActivity.this);
            new ToastDialog().show(getSupportFragmentManager(),"");
        });

        findViewById(R.id.btnCheckRoot).setOnClickListener(v -> {
//           boolean isRoot =  CheckXpUtils.checkRoot();
            RootCheck.checkRoot();
//            Toast.makeText(ProxyActivity.this,isRoot?"设备已root":"设备未取得root",Toast.LENGTH_LONG).show();
        });
        findViewById(R.id.btnCheckXp).setOnClickListener(v -> {
//            CheckXpUtils.checkXposed(ProxyActivity.this);

//            viewFlipper.startFlipping();
            startActivity(new Intent(ProxyActivity.this,MainActivity.class));
        });

        findViewById(R.id.btnLiveBus).setOnClickListener(v -> {
//            LiveDataBus.get().with("smart").setValue("hello smart");
//            View view = findViewById(R.id.rootlayout);
//            int width = (int) ProxyActivity.this.getResources().getDimension(R.dimen.item_width);
//            ValAnimUtils.startZoomAnim(view,width);
        });

        LiveDataBus.get().with("smart", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                System.out.println("====data=====" + s);
            }
        });

        findViewById(R.id.tvClose).setOnClickListener(v -> {
            View view = findViewById(R.id.rootlayout);
            ValAnimUtils.startZoomAnim(view, 0);
        });


    }


    /**
     * 动态代理，就是不改变之前的代码逻辑，在原来的基础上，增强功能；（业务之前，增加功能，业务之后增加功能）
     */
    void proxy() {
        SuperStar superStar = new SuperStar();
        Star st = new SubInvocationHandler<Star>(superStar,
                new InvokeCallBackInterface() {
                    @Override
                    public void beforeMethod(MethodParam param) {
                        System.out.println("===代理执行前==" + param.args[0]);
                        param.args[0] = "李四";
                        System.out.println("===代理执行前=改变后的==" + param.args[0]);
                    }

                    @Override
                    public void afterMethod(MethodParam param) {
                        System.out.println("===代理执行后===" + param.args[0]);
                        System.out.println("===代理执行后=res==" + param.getResult());
                        System.out.println("===代理执行后=method==" + param.getMethod().getName());
                    }

                }).newProxyInstance();
        st.song("张三");

    }




}
