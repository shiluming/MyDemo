package com.slm.netty.callback;

public class Work {

    public void onWork() {
        //请求上网
        NetWork netWork = new NetWork();
        netWork.netWork(new DefaulCallBack());

    }
}
