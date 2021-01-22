package com.h.grallerydemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.WindowManager;

public class LiveBoottomDiaolog extends BottomDialogFragment {

    private onLiveDismissInterface mOnLiveDismissInterface;


    public void setmOnLiveDismissInterface(onLiveDismissInterface mOnLiveDismissInterface) {
        this.mOnLiveDismissInterface = mOnLiveDismissInterface;
    }

    public static void show(FragmentManager fm, onLiveDismissInterface onLiveDismissInterface) {
        LiveBoottomDiaolog diaolog = new LiveBoottomDiaolog();
        diaolog.setmOnLiveDismissInterface(onLiveDismissInterface);
        diaolog.show(fm, LiveBoottomDiaolog.class.getName());
    }


    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.dimAmount = 0.0f;
        getDialog().getWindow().setAttributes(layoutParams);
    }

    @Override
    public int getLayoutView() {
        return R.layout.bottom_layout;
    }

    @Override
    public void dismiss() {

        super.dismiss();

    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        System.out.println("======dismiss=========");
        if (null != mOnLiveDismissInterface)
            mOnLiveDismissInterface.dimiss();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnLiveDismissInterface)
                    mOnLiveDismissInterface.dimiss();
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public interface onLiveDismissInterface {
        void dimiss();
    }
}
