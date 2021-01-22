package com.h.grallerydemo.impl;

import com.h.grallerydemo.interfaces.Star;

public class SuperStar implements Star {

    @Override
    public String song(String hello) {
        String res = hello + "=====唱歌了";
        System.out.println(res);
        return res;
    }

    @Override
    public String pk(String hello) {
        return "PK result " + hello;
    }
}
