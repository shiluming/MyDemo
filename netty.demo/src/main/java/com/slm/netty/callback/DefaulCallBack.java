package com.slm.netty.callback;

public class DefaulCallBack implements ICallBack {

    public String success() {
        System.out.println(Thread.currentThread().getName() + " 上网成功 ");
        return "success";
    }

    public String fail() {
        System.out.println(Thread.currentThread().getName() + " 上网失败 ");
        return "fail";
    }
}
